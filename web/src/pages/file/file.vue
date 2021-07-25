<template>
    <div class="file-main">
        <div class="el-card" style="height: 100%;">
            <div class="file-option">
                <el-upload
                    action=""
                    :show-file-list="false"
                    :http-request="on_upload">
                    <el-button size="mini" type="primary">上传</el-button>
                </el-upload>
                <el-dropdown size="small" type="primary" style="padding-left:12px;">
                    <el-button size="mini" class="file-button">新建</el-button>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="mkdir">新建文件夹</el-dropdown-item>
                        <el-dropdown-item @click.native="touch">新建文件</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-button-group style="padding-left:12px;" v-if="paths.length > 0">
                    <el-button size="mini" class="file-button" @click="multi_download">下载</el-button>
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
                        <el-checkbox :indeterminate="is_check_all" 
                            v-model="check_all" 
                            @change="handleCheckAllChange"
                            :disabled="file_list.length === 0"></el-checkbox>
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
                        @contextmenu.prevent="open_menu(index, file, $event)"
                        :class="{'file-content-hover': index === menu_index}"
					>
                        <div class="file-name">
                            <el-checkbox :label="file.path">
                                <i class="el-icon-folder-opened" v-if="file.type === 'FOLDER'"></i>
                                <i class="el-icon-document" v-else-if="file.type === 'FILE'"></i>
                            </el-checkbox>
                            <span>{{ file.name }}</span>
                        </div>
                        <div class="file-item-center">{{ file.size }}</div>
                        <div>{{ file.update_time }}</div>
                        <div>{{ file.permission }}</div>
                        <div>{{ file.group }} {{ file.owner }}</div>
                        <div>
                            <el-button type="text" size="mini" @click="rm(file.path, false)">删除</el-button>
                            <el-button type="text" size="mini" @click="rm(file.path, true)">强制删除</el-button>
                            <el-button type="text" size="mini" @click="rename(file.name)">重命名</el-button>
                            <el-button v-if="file.type === 'FILE'" type="text" size="mini" @click="download(file.path, file.name)">下载</el-button>
                        </div>
                    </div>
                </div>
                </el-checkbox-group>
            </div>
        </div>
        <div id="file-menu-bg" @click="close_menu" @contextmenu.prevent="close_menu"></div>
        <div id="file-menu">
            <div @click="menu_open">打开</div>
            <div v-if="this.menu_file && this.menu_file.type === 'FILE'" @click="menu_open_by_code">文本格式打开</div>
            <div v-if="this.menu_file && this.menu_file.type === 'FILE'">图片格式打开</div>
            <div v-if="this.menu_file && this.menu_file.type === 'FILE'">视频格式打开</div>
            <div v-if="this.menu_file && this.menu_file.type === 'FILE'">下载</div>
            <div @click="() => {rm(menu_file.path, false);close_menu()}">删除</div>
            <div @click="() => {rename(menu_file.name);close_menu()}">重命名</div>
        </div>
        <el-dialog
            title="代码编辑器"
            :visible.sync="code"
            :modal-append-to-body='false'
            destroy-on-close
            :close-on-click-modal="false"
            width="70%">
                <div>
                    <span>代码主题：</span>
                    <el-select v-model="options.theme" placeholder="请选择主题" style="margin-bottom: 12px;">
                        <el-option
                        v-for="theme in themes"
                        :key="theme"
                        :label="theme"
                        :value="theme">
                        </el-option>
                    </el-select>
                    <span style="padding-left: 12px">语法：</span>
                    <el-select v-model="options.mode" placeholder="请选择主题" style="margin-bottom: 12px;">
                        <el-option
                        v-for="(mode, index) in modes"
                        :key="index"
                        :label="mode.label"
                        :value="mode.value">
                        </el-option>
                    </el-select>
                    <span style="padding-left: 12px">编码：</span>
                    <el-select v-model="charset" placeholder="请选择主题" style="margin-bottom: 12px;" @change="open_code_charset">
                        <el-option
                        v-for="(charset, index) in charsets"
                        :key="index"
                        :label="charset"
                        :value="charset">
                        </el-option>
                    </el-select>
                </div>
                <codemirror v-model="content" :options="options"></codemirror>
                 <span slot="footer" class="dialog-footer">
                    <el-button @click="code = false">取 消</el-button>
                    <el-button type="primary" @click="write_code_charset">修 改</el-button>
                </span>
        </el-dialog>
    </div>
</template>

<script src="./file.js"></script>

<style scoped>
@import url(./file.css);
</style>