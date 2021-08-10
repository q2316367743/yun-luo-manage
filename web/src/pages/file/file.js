// 引入API
import file from '@/apis/file.js'
import setting from '@/setting';

import code_edit from '@/components/code_edit.vue'

import 'mui-player/dist/mui-player.min.css'
import MuiPlayer from 'mui-player'
import Flv from 'flv.js'

function dl(content, fileName) {
    var aEle = document.createElement("a"); // 创建a标签
    aEle.download = fileName; // 设置下载文件的文件名
    aEle.href = URL.createObjectURL(content);
    aEle.click(); // 设置点击事件
}
// 自定义工具
function lastWith(str, target) {
    // 请把你的代码写在这里
    var start = str.length - target.length;
    var arr = str.substr(start, target.length);
    if (arr == target) {
        return true;
    }
    return false;
}

export default {
    name: "file",
    components: {
        "code-edit": code_edit
    },
    data: () => ({
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
        code: false,
        code_path: '',
        remote_download_status: false,
        remote_download_value: {
            name: '',
            url: ''
        },
        to_path_temp: '',
        to_path_status: false,
        suffix: ''
    }),
    created() {
        // 获取用户目录
        file.base(res => {
            this.path = res.data.item;
            this.code_style = res.data.items;
            this.parse_path();
            this.toPath();
        })
    },
    methods: {
        toP(path) {
            let old = this.path;
            this.path = path;
            this.toPath(() => {
                this.path = old;
                this.parse_path();
            });
            this.clear_check();
            this.parse_path();
        },
        toPath(error) {
            file.ls(this.path, res => {
                if (res.success) {
                    this.file_list = res.data.items;
                }
            }, error)
        },
        parse_path() {
            let show_path = [{
                name: '根目录',
                path: '/'
            }];
            if (this.path === '/') {
                this.show_path = show_path;
                return;
            }
            let items = this.path.split('/');
            let path = '';
            for (let item of items) {
                if (item === '') {
                    continue;
                }
                path = path + '/' + item;
                show_path.push({
                    name: item,
                    path
                })
            }
            this.show_path = show_path;
        },
        handleCheckAllChange(val) {
            console.log(val)
            if (val) {
                let paths = [];
                this.file_list.forEach(item => {
                    paths.push(item.path);
                });
                this.paths = paths;
            } else {
                this.paths = [];
            }
            this.is_check_all = false;
        },
        handleCheckedChange(value) {
            this.check_all = value.length === this.file_list.length;
            this.is_check_all = value.length > 0 && value.length < this.file_list.length;
        },
        /**
         * 打开
         * 
         * @param {Object} file 文件详情
         */
        open(file) {
            if (file.type === 'FOLDER') {
                this.toP(file.path)
            } else if (file.type === 'FILE') {
                this.open_by_file(file.path, file.name);
            }
        },
        /**
         * 打开文件
         * @param {String} path 文件路径
         * @param {String} name 文件名
         */
        open_by_file(path, name) {
            this.menu_temp_path = path;
            this.suffix = '';
            // 判断是否是文本类型
            for (let item of setting.file_type.code) {
                if (lastWith(name, item)) {
                    this.open_by_code(name, item)
                    return;
                }
            }
            for (let item of setting.file_type.img) {
                if (lastWith(name, item)) {
                    this.open_by_image(name)
                    return;
                }
            }
            for (let item of setting.file_type.video) {
                if (lastWith(name, item)) {
                    this.open_by_video(name)
                    return;
                }
            }
            this.$message.error('不受支持的文件类型')
        },
        open_by_code(path, suffix) {
            this.code = true;
            this.code_path = path;
            this.suffix = suffix;
        },
        open_by_image(name) {
            let img = document.createElement('img');
            img.style.height = '500px'
            img.src = `${setting.base_url}/file/show?path=${this.menu_temp_path}&token=${sessionStorage.getItem('token')}`;
            this.$layer.open({
                type: 1,
                title: name,
                area: ['auto'],
                shadeClose: true,
                content: img.outerHTML
            });
        },
        open_by_video(name) {
            let src = `${setting.base_url}/file/show?path=${this.menu_temp_path}&token=${sessionStorage.getItem('token')}`;
            if (lastWith(name, 'flv')) {
                this.$layer.open({
                    type: 1,
                    title: name,
                    area: ['800px', '501px'],
                    shadeClose: false,
                    content: '<div id="mui-player"></div>',
                    success() {
                        new MuiPlayer({
                            container: '#mui-player',
                            title: name,
                            src: src,
                            autoplay: true,
                            height: '500px',
                            width: '800px',
                            parse: {
                                type: 'flv',
                                loader: Flv, // flv config to：https://github.com/Bilibili/flv.js/blob/HEAD/docs/api.md#flvjscreateplayer
                                config: {
                                    cors: true
                                },
                            },
                        });


                    }
                });
            } else {
                this.$layer.open({
                    type: 1,
                    title: name,
                    shadeClose: false,
                    content: '<div id="mui-player"></div>',
                    success() {
                        new MuiPlayer({
                            container: '#mui-player',
                            title: name,
                            src: src,
                            autoplay: true,
                            height: '500px',
                            width: '800px',
                        });

                    }
                });

            }

        },
        rename(name) {
            let path = this.path;
            this.$prompt('请输入新名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputValue: name
            }).then(({ value }) => {
                file.rename(path, name, value, res => {
                    if (res.success) {
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
        touch() {
            let path = this.path;
            this.$prompt('请输入文件名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(({ value }) => {
                file.touch(path, value, res => {
                    if (res.success) {
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
        mkdir() {
            let path = this.path;
            this.$prompt('请输入文件夹名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then(({ value }) => {
                file.mkdir(path, value, res => {
                    if (res.success) {
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
        rm(path, is_force) {
            this.$confirm('此操作将永久删除该文件/夹, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                file.rm([path], is_force, res => {
                    if (res.success) {
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
        rm_all() {
            this.$confirm('此操作将永久并强制删除这些文件/夹, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                file.rm(this.paths, true, res => {
                    if (res.success) {
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
        clear_check() {
            this.paths = [];
            this.check_all = false;
            this.is_check_all = false
        },
        cp() {
            this.cp_paths = this.paths;
            this.clear_check()
        },
        mv() {
            this.mv_paths = this.paths;
            this.clear_check();
        },
        paste() {
            console.log(this.mv_paths, this.cp_paths, this.path);
            if (this.mv_paths.length > 0) {
                file.mv(this.mv_paths, this.path, res => {
                    if (res.success) {
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
            if (this.cp_paths.length > 0) {
                file.cp(this.cp_paths, this.path, res => {
                    if (res.success) {
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
        canel() {
            this.cp_paths = [];
            this.mv_paths = [];
        },
        open_menu(index, file, e) {
            this.menu_index = index;
            this.menu_file = file;
            this.menu_temp_path = file.path;
            let menu = document.getElementById('file-menu');
            menu.style.display = 'block';
            menu.style.left = e.layerX + 10 + 'px'
            menu.style.top = e.layerY + 10 + 'px';
            document.getElementById('file-menu-bg').style.display = 'block';
        },
        close_menu() {
            document.getElementById('file-menu').style.display = 'none';
            document.getElementById('file-menu-bg').style.display = 'none';
            this.menu_index = -1;
            this.menu_file = null;
        },
        write_code_charset() {
            // 以指定的编码写入
            file.write(this.menu_temp_path, this.$refs.code_edit.get_charset(), this.$refs.code_edit.get_content(), res => {
                if (res.success) {
                    // 关闭对话框
                    this.code = false
                    this.$message.success('修改成功')
                }
            }, message => {
                this.$message.error('修改失败，' + message)
            })
        },
        /**
         * 菜单打开
         */
        menu_open() {
            this.open(this.menu_file);
            this.close_menu();
        },
        menu_open_by_code() {
            let code = setting.file_type.code;
            this.suffix = '';
            for (let item of code) {
                if (lastWith(this.menu_file.name, item)) {
                    this.suffix = item;
                    break;
                }
            }
            this.code = true;
            this.code_path = this.menu_file.path;
        },
        download(path, name) {
            file.show(path, res => {
                dl(res, name)
            }, () => {
                this.$message.error('文件下载失败')
            })
        },
        open_remote_download() {
            this.remote_download_status = true
            this.remote_download_value = {
                name: '',
                url: ''
            }
        },
        multi_download() {
            this.$message({
                message: '暂不可用',
                type: 'warning'
            })
        },
        on_upload(event) {
            let formData = new FormData();
            formData.set("path", this.path);
            formData.set("file", event.file);
            file.upload(formData, res => {
                if (res.success) {
                    this.$message.success('上传成功');
                    // 重新加载目录
                    this.toP(this.path);
                }
            }, () => {
                this.$message.error('上传失败');
            })

        },
        refresh() {
            this.toP(this.path);
            this.close_menu();
        },
        remote_download() {
            this.remote_download_status = false;
            let url = this.remote_download_value.url;
            if (url.length === 0) {
                this.$message.error('url不能为空');
            }
            // 判断是否是url
            let oRegUrl = new RegExp();
            oRegUrl.compile("^[A-Za-z]+://");
            if (!oRegUrl.test(url)) {
                this.$message.error('url格式不正确');
            }
            file.remote_download(this.path, this.remote_download_value.name, url, (res) => {
                if (res.success) {
                    this.$message.success('下载成功');
                    this.refresh()
                }
            }, () => {
                this.$message.error('下载失败')
            })

        },
        open_path_input() {
            this.to_path_status = true;
            this.to_path_temp = this.path;
            this.$nextTick(() => {
                let input = this.$refs.to_path_input;
                input.focus();
            })
        },
        path_input_to() {
            this.to_path_status = false;
            if (this.path === this.to_path_temp) {
                return;
            }
            this.toP(this.to_path_temp)
        },
        cancel_path_input() {
            this.to_path_status = false;
        }
    }
}