<template>
    <div class="file-main">
        <div class="el-card" style="height: 100%;">
            <div class="file-option">
                <el-button size="mini" type="primary">上传</el-button>
                <el-dropdown size="small" type="primary" style="padding-left:12px;">
                    <el-button size="mini" class="file-button">新建</el-button>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="mkdir">新建文件夹</el-dropdown-item>
                        <el-dropdown-item @click.native="touch">新建文件</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-button-group style="padding-left:12px;" v-if="paths.length > 0">
                    <el-button size="mini" class="file-button">下载</el-button>
                    <el-button size="mini" class="file-button" @click="rm_all">删除</el-button>
                    <el-button size="mini" class="file-button" @click="mv" :disabled="mv_paths.length > 0 || cp_paths.length > 0">移动到</el-button>
                    <el-button size="mini" class="file-button" @click="cp" :disabled="mv_paths.length > 0 || cp_paths.length > 0">复制到</el-button>
                </el-button-group>
                <el-button style="margin-left:12px;" size="mini" @click="paste" class="file-button" v-if="mv_paths.length > 0 || cp_paths.length > 0">黏贴到此处</el-button>
                <el-button style="margin-left:12px;" size="mini" @click="canel" class="file-button" 
                    v-if="mv_paths.length > 0 || cp_paths.length > 0">取消{{mv_paths.length > 0 ? '移动' : '复制'}}</el-button>
            </div>
            <div class="file-path">
                <span v-for="(item, index) in show_path" :key="index">
                    <span v-if="index != 0" style="padding: 0 3px;">/</span>
                    <el-button type="text" @click="toP(item.path)">{{ item.name }}</el-button>
                </span>
            </div>
            <div>
                <div class="file-head">
                    <div class="file-name">
                        <el-checkbox :indeterminate="is_check_all" v-model="check_all" @change="handleCheckAllChange"></el-checkbox>
                        <span>文件名</span>
                    </div>
                    <div class="file-item-center">文件大小</div>
                    <div>修改时间</div>
                    <div>权限</div>
                    <div>所有者</div>
                    <div>操作</div>
                </div>
                <el-checkbox-group v-model="paths" @change="handleCheckedChange">
                <div class="file-content">
                    <div 
						class="file-content-item" 
						v-for="(file, index) in file_list" 
						:key="index"
						@dblclick="file.type == 'FOLDER' ? toP(file.path) : open(file.path)"
					>
                        <div class="file-name">
                            <el-checkbox :label="file.path">
                                <i class="el-icon-folder-opened" v-if="file.type === 'FOLDER'"></i>
                                <i class="el-icon-document" v-else-if="file.type === 'FILE'"></i>
                                <span style="padding-left: 3px;">{{ file.name }}</span>
                            </el-checkbox>
                        </div>
                        <div class="file-item-center">{{ file.size }}</div>
                        <div>{{ file.update_time }}</div>
                        <div>{{ file.permission }}</div>
                        <div>{{ file.group }} {{ file.owner }}</div>
                        <div>
                            <el-button type="text" size="mini">下载</el-button>
                            <el-button type="text" size="mini" @click="rm(file.path, false)">删除</el-button>
                            <el-button type="text" size="mini" @click="rm(file.path, true)">强制删除</el-button>
                            <el-button type="text" size="mini" @click="rename(file.name)">重命名</el-button>
                        </div>
                    </div>
                </div>
                </el-checkbox-group>
            </div>
        </div>
    </div>
</template>

<script>
import file from '@/apis/file.js'

export default {
    name: "file",
    data: ()=>({
        path: '/',
        show_path: [],
        file_list: [],
        paths: [],
        check_all: false,
        is_check_all: false,
        cp_paths: [],
        mv_paths: []
    }),
    created(){
        // 获取用户目录
        file.base(res=> {
            this.path = res.data.item;
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
        }
    }
}
</script>

<style scoped>
.file-main{
    padding: 12px
}
.file-path{
    padding-left: 12px;
}
.file-option{
    padding-left: 12px;
    padding-top: 12px;
}
.file-item-center{
    text-align: center;
}
.file-content>div{
    height: 45px;
    align-items: center;
}
.file-content>div:hover{
    background-color: var(--em-color);
    cursor: pointer;
}
.file-name>.el-checkbox{
    padding-left: 8px;
    padding-right: 8px;
}
.file-head{
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: 2fr 1fr 1.5fr 0.7fr 0.7fr 1.1fr;
}
.file-content-item{
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: 2fr 1fr 1.5fr 0.7fr 0.7fr 1.1fr;
}
.file-button{
    color: var(--text-color) !important;
    background-color: var(--main-color) !important;
}
</style>