<template>
    <el-card style="margin: 12px">
        <div class="config-open">
            <el-button type="primary">读取</el-button>
            <el-button type="primary" @click="download">下载</el-button>
        </div>
        <el-tabs v-model="activeName">
            <el-tab-pane label="全局配置" name="main">
                <el-form label-width="180px" label-position="left">
                    <el-form-item label="Windows系统：">
                        <el-switch
                            v-model="result.is_window"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            :active-value="true"
                            :inactive-value="false"
                        >
                        </el-switch>
                    </el-form-item>

                    <el-form-item label="Nginx用户及组：">
                        <el-input type="text" v-model="result.user.info" :disabled="result.is_window"
                                  style="width: 200px"/>
                        <el-input
                            type="text"
                            v-model="result.user.group"
                            :disabled="result.is_window"
                            style="width: 200px;margin-left: 20px;"
                        />
                    </el-form-item>
                    <el-form-item label="允许生成的进程数：">
                        <el-input-number v-model="result.worker_processes" style="width: 164px;" controls-position="right"></el-input-number>
                    </el-form-item>
                    <el-form-item label="运行文件存放地址：">
                        <el-input v-model="result.pid" style="width: 348px;"/>
                    </el-form-item>
                    <el-form-item label="日志路径，级别：">
                        <el-input type="text" v-model="result.error_log" style="width: 348px;"/>
                        <el-select
                            v-model="result.error_log_level"
                            style="margin-left: 10px;width: 164px;"
                        >
                            <el-option
                                v-for="item in ['debug', 'info', 'notice', 'warn', 'error', 'crit', 'alert', 'emerg']"
                                :key="item"
                                :label="item"
                                :value="item">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="events配置" name="events">
                <el-form label-width="180px" label-position="left">
                    <el-form-item label="网路连接序列化：">
                        <el-switch
                            v-model="result.events.accept_mutex"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                        active-value="open"
                        inactive-value="close">
                        </el-switch>
                    </el-form-item>
                    <el-form-item label="多连接：">
                        <el-switch
                            v-model="result.events.multi_accept"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-value="open"
                            inactive-value="close">
                        </el-switch>
                    </el-form-item>
                    <el-form-item label="事件驱动模型：">
                        <el-select
                            v-model="result.events.use"
                            style="width: 164px;"
                        >
                            <el-option
                                v-for="item in ['select', 'poll', 'kqueue', 'epoll', 'resig', '/dev/poll', 'eventport']"
                                :key="item"
                                :label="item"
                                :value="item">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="最大连接数：">
                        <el-input-number v-model="result.events.worker_connections" style="width: 164px;" controls-position="right"></el-input-number>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="http配置" name="http">角色管理</el-tab-pane>
            <el-tab-pane label="预览" name="show">
                <div class="nginx-show">
                    <pre ref="download">
{{result.isWindow ? '' : `user ${result.user.info} ${result.user.group};\n`}}worker_processes {{result.worker_processes}};
pid {{result.pid}};
error_log {{result.error_log}} {{result.error_log_level}};
events{
    accept_mutex: {{result.events.accept_mutex ? 'on' : 'off'}};
    multi_accept {{result.events.multi_accept ? 'on' : 'off'}};{{result.isWindow ? '' : `\n    use ${result.events.use};`}}
    worker_connections {{result.events.worker_connections}};
}
            </pre>
                </div>
            </el-tab-pane>
        </el-tabs>
    </el-card>
</template>

<script>
export default {
    name: "config",
    data: () => ({
        tabs: {
            main: "全局配置",
            events: "events配置",
            http: "http配置",
            show: "预览",
        },
        activeName: 'main',
        currentTab: "main",
        result: {
            is_window: false,
            user: {
                info: "nobody",
                group: "nobody"
            },
            worker_processes: 1,
            pid: "/nginx/pid/nginx.pid",
            error_log: "log/error.log",
            error_log_level: "debug",
            events: {
                accept_mutex: true,
                multi_accept: false,
                use: 'epoll',
                worker_connections: 512
            },
        },
    }),
    methods: {
        download() {
            let eleLink = document.createElement("a");
            eleLink.download = 'nginx.conf';
            eleLink.style.display = "none";
            // 字符内容转变成blob地址
            let blob = new Blob([this.$refs.download.innerText]);
            eleLink.href = URL.createObjectURL(blob);
            // 触发点击
            document.body.appendChild(eleLink);
            eleLink.click();
            // 然后移除
            document.body.removeChild(eleLink);
        },
    }
}
</script>

<style lang="scss" scoped>
.nginx-show {
    margin-top: 20px;
    position: relative;
    pre {
        padding: 5px 10px;
        background-color: #282c34;
        color: #fff;
        font-family: JetBrainsMono, sans-serif;
        font-size: 16px;
        letter-spacing: 1px;
    }
}
.config-open{
    position: absolute;
    top: 22px;
    right: 32px;
    z-index: 1;
}
</style>