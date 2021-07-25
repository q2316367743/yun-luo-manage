// 引入API
import file from '@/apis/file.js'

// 引入CodeMirror
import { codemirror } from 'vue-codemirror'
// 核心样式
import 'codemirror/lib/codemirror.css'
// 引入主题后还需要在 options 中指定主题才会生效
import 'codemirror/theme/idea.css'
import 'codemirror/theme/rubyblue.css'
import 'codemirror/theme/xq-dark.css'
import 'codemirror/theme/cobalt.css'
import 'codemirror/theme/dracula.css'
// 语法高亮
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/css/css.js'
import 'codemirror/mode/xml/xml.js'
import 'codemirror/mode/markdown/markdown.js'
import 'codemirror/mode/python/python.js'
import 'codemirror/mode/shell/shell.js'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/mode/vue/vue.js'

// 自定义工具
function lastWith(str, target) {
    // 请把你的代码写在这里
    var start = str.length-target.length;
    var arr = str.substr(start,target.length);
    if(arr == target){
        return true;
    }
    return false;
}

function dl(content, fileName) {
    var aEle = document.createElement("a");// 创建a标签
    aEle.download = fileName;// 设置下载文件的文件名
    aEle.href = URL.createObjectURL(content);
    aEle.click();// 设置点击事件
  }

const default_charset = 'UTF-8';

export default {
    name: "file",
    components: {
        codemirror
    },
    data: ()=>({
        path: '/',
        show_path: [],
        file_list: [],
        paths: [],
        check_all: false,
        is_check_all: false,
        cp_paths: [],
        mv_paths: [],
        menu_file: null,
        menu_temp_path: '',
        menu_index: -1,
        // 代码
        content: 'print(\'Hello World\')',
        themes: ['idea', 'rubyblue', 'xq-dark', 'cobalt', 'dracula'],
        charsets: ['UTF-8', 'ISO-8859-1', 'GBK'],
        charset: '',
        modes: [{
            value: '',
            label: '不使用语法主题',
            suffix: ['text']
        }, {
            value: 'css',
            label: 'CSS',
            suffix: ['css']
        }, {
            value: 'javascript',
            label: 'Java/Javascript',
            suffix: ['js', 'java']
        }, {
            value: 'html',
            label: 'XML/HTML',
            suffix: ['html', 'xml']
        }, {
            value: 'x-python',
            label: 'Python',
            suffix: ['py']
        }, {
            value: 'x-sh',
            label: 'Shell',
            suffix: ['sh']
        }, {
            value: 'sql',
            label: 'SQL',
            suffix: ['sql']
        }, {
            value: 'vue',
            label: 'Vue',
            suffix: ['vue']
        }, {
            value: 'markdown',
            label: 'Markdown',
            suffix: ['md']
        }],
		// 编辑器默认配置
        options: {
            tabSize: 4, // 缩进格式
            theme: 'dracula', // 主题，对应主题库 JS 需要提前引入
            lineNumbers: true, // 显示行号
            line: true,
            mode: '',
            styleActiveLine: true, // 高亮选中行
            hintOptions: {
                completeSingle: true // 当匹配只有一项的时候是否自动补全
            }
        },
        code: false,
        code_style: {},
    }),
    created(){
        // 获取用户目录
        file.base(res=> {
            this.path = res.data.item;
            this.code_style = res.data.items;
            this.parse_path();
            this.toPath();
        })
    },
    methods: {
        toP(path){
            let old = this.path;
            this.path = path;
            this.toPath(() => {
                this.path = old;
                this.parse_path();
            });
            this.clear_check();
			this.parse_path();
        },
        toPath(error){
            file.ls(this.path, res=>{
                if(res.success){
                    this.file_list = res.data.items;
                }
            }, () => {
                if(error){
                    error()
                }
            })
        },
        parse_path(){
            let show_path = [{
                name: '根目录',
                path: '/'
            }];
            if(this.path === '/'){
                this.show_path = show_path;
                return;
            }
            let items = this.path.split('/');
            let path = '';
            for(let i = 0; i < items.length; i++){
                if(items[i] === ''){
                    continue;
                }
                path = path + '/' + items[i];
                show_path.push({
                    name: items[i],
                    path
                })
            }
            this.show_path = show_path;
        },
        handleCheckAllChange(val){
            console.log(val)
            if(val){
                let paths = [];
                this.file_list.forEach(item => {
                    paths.push(item.path);
                });
                this.paths = paths;
            }else{
                this.paths = [];
            }
            this.is_check_all = false;
        },
        handleCheckedChange(value){
            this.check_all = value.length === this.file_list.length;
            this.is_check_all = value.length > 0 && value.length < this.file_list.length;
        },
        open(path){
            console.log(path);
        },
        rename(name){
            let path = this.path;
            this.$prompt('请输入新名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputValue: name
            }).then(({ value }) => {
                file.rename(path, name, value, res=>{
                    if(res.success){
                        this.$message({
                        type: 'success',
                        message: '修改成功'
                    });
                    // 重新加载目录
                    this.toP(this.path);
                    }
                })
            })
        },
        touch(){
            let path = this.path;
            this.$prompt('请输入文件名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(({ value }) => {
                file.touch(path, value, res=>{
                    if(res.success){
                        this.$message({
                        type: 'success',
                        message: '创建文件成功'
                    });
                    // 重新加载目录
                    this.toP(this.path);
                    }
                })
            })
        },
        mkdir(){
            let path = this.path;
            this.$prompt('请输入文件夹名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(({ value }) => {
                file.mkdir(path, value, res=>{
                    if(res.success){
                        this.$message({
                        type: 'success',
                        message: '创建文件夹成功'
                    });
                    // 重新加载目录
                    this.toP(this.path);
                    }
                })
            })
        },
        rm(path, is_force){
            this.$confirm('此操作将永久删除该文件/夹, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                file.rm([path], is_force, res=>{
                    if(res.success){
                            this.$message({
                            type: 'success',
                            message: '删除该文件/夹成功'
                        });
                        // 重新加载目录
                        this.toP(this.path);
                    }
                })
            })
        },
        rm_all(){
            this.$confirm('此操作将永久并强制删除这些文件/夹, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                file.rm(this.paths, true, res=>{
                    if(res.success){
                            this.$message({
                            type: 'success',
                            message: '删除这些文件/夹成功'
                        });
                        // 重新加载目录
                        this.toP(this.path);
                    }
                })
            })
        },
        clear_check(){
            this.paths = [];
            this.check_all = false;
            this.is_check_all = false
        },
        cp(){
            this.cp_paths = this.paths;
            this.clear_check()
        },
        mv(){
            this.mv_paths = this.paths;
            this.clear_check();
        },
        paste(){
            console.log(this.mv_paths, this.cp_paths, this.path);
            if(this.mv_paths.length > 0){
                file.mv(this.mv_paths, this.path, res=> {
                    if(res.success){
                            this.$message({
                            type: 'success',
                            message: '移动成功'
                        });
                        // 重新加载目录
                        this.toP(this.path);
                        this.canel();
                    }
                })
            }
            if(this.cp_paths.length > 0){
                file.cp(this.cp_paths, this.path, res=> {
                    if(res.success){
                            this.$message({
                            type: 'success',
                            message: '复制成功'
                        });
                        // 重新加载目录
                        this.toP(this.path);
                        this.canel();
                    }
                })
            }
        },
        canel(){
            this.cp_paths = [];
            this.mv_paths = [];
        },
        open_menu(index, file, e){
            this.menu_index = index;
            this.menu_file = file;
            let menu = document.getElementById('file-menu');
            menu.style.display = 'block';
            menu.style.left = e.layerX + 10 + 'px'
            menu.style.top = e.layerY + 10 + 'px';
            document.getElementById('file-menu-bg').style.display = 'block';
        },
        close_menu(){
            document.getElementById('file-menu').style.display = 'none';
            document.getElementById('file-menu-bg').style.display = 'none';
            this.menu_index = -1;
            this.menu_file = null;
        },
        open_code(){
            this.code = true;
            // 获取文件内容
            this.charset = default_charset;
            this.menu_temp_path = this.menu_file.path;
            this.open_code_charset();
        },
        open_code_charset(){
            // 以指定编码打开
            file.open(this.menu_temp_path, this.charset, res=>{
                if(res.success){
                    this.content = res.data.item;
                }
            })
        },
        write_code_charset(){
            // 以指定的编码写入
            file.write(this.menu_temp_path, this.charset, this.content, res => {
                if(res.success){
                    // 关闭对话框
                    this.code = false
                    this.$message.success('修改成功')
                }
            }, message => {
                this.$message.error('修改失败，' + message)
            })
        },
        menu_open(){
            if(this.menu_file.type === 'FOLDER'){
                this.toP(this.menu_file.path)
            }else if(this.menu_file.type === 'FILE'){
                let is_error = true;
                // 文件类型，通过判断后缀判断具体类型
                let name = this.menu_file.name;
                // 文本类型
                let code = this.code_style.code;
                for(let item of code){
                    if(lastWith(name, item)){
                        // 判断是否是支持的语法高亮
                        let is_no_suppert = true;
                        for(let mode of this.modes){
                            for(let suffix of mode.suffix){
                                if(item === suffix){
                                    is_no_suppert = false;
                                    this.options.mode = mode.value;
                                    break;
                                }
                            }
                        }
                        if(is_no_suppert){
                            this.options.mode = '';
                        }
                        this.open_code();
                        is_error = false;
                        break;
                    }
                }
                if(is_error){
                    this.$message.error('不受支持的文件类型')
                }
            }
            this.close_menu();
        },
        menu_open_by_code(){
            // 以文本形式打开
            // 文件类型，通过判断后缀判断具体类型
            let name = this.menu_file.name;
            // 文本类型
            let code = this.code_style.code;
            for(let item of code){
                if(lastWith(name, item)){
                    // 判断是否是支持的语法高亮
                    let is_no_suppert = true;
                    for(let mode of this.modes){
                        for(let suffix of mode.suffix){
                            if(item === suffix){
                                is_no_suppert = false;
                                this.options.mode = mode.value;
                                break;
                            }
                        }
                    }
                    if(is_no_suppert){
                        this.options.mode = '';
                    }
                    break;
                }
            }
            this.open_code();
            this.close_menu();
        },
        download(path, name){
            file.show(path, res=>{
                dl(res, name)
            }, ()=>{
                this.$message.error('文件下载失败')
            })
        },
        multi_download(){
            this.$message({
                message: '暂不可用',
                type: 'warning'
            })
        },
        on_upload(event){
            let formData = new FormData();
            formData.set("path", this.path);
            formData.set("file", event.file);
            file.upload(formData, res=>{
                if(res.success){
                    this.$message.success('上传成功');
                    // 重新加载目录
                    this.toP(this.path);
                }
            }, ()=>{
                this.$message.error('上传失败');
            })
            
        }
    }
}