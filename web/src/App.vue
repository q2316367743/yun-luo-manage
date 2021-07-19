<template>
    <div id="app">
        <div id="left-nav">
            <div id="logo">
                <div id="title">云落管理</div>
                <div id="subtitle">Linux</div>
            </div>
            <el-menu
                :default-active="index"
                router>
                <template v-for="(item, index) in router">
                    <el-submenu :key="index" :index="item.path" v-if="item.children">
                        <template slot="title">{{ item.nickname }}</template>
                        <el-menu-item v-for="(child, child_index) in item.children" :key="child_index" :index="item.path + '/' + child.path" v-text="child.nickname"></el-menu-item>
                    </el-submenu>
                    <el-menu-item :key="index" :index="item.path" v-text="item.nickname" v-else></el-menu-item>
                </template>
            </el-menu>
        </div>

        <div id="top-nav">
            <div></div>
            <div>
                <el-dropdown>
                    <span>管理员</span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>退出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

        <!-- 根据应用组件来调整你的内容 -->
        <div id="main">
            <router-view></router-view>
        </div>

        <div id="footer">
            <span>云落管理 ©2014-2021 云落天都 | 项目地址：</span>
            <a href="https://gitee.com/qiaoshengda/yun-luo-manage" target="_blank">码云</a>
        </div>

    </div>
</template>

<script>
import router from './plugins/router'

export default {
    name: 'App',

    data: () => ({
        index: '/',
        router: router
    }),
    watch: {
        $route(to){
            this.index = to.path;
        }
    },
    created() {
        this.index = this.$route.path
    }
};
</script>

<style>
#app {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    overflow: hidden;
}

#left-nav {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 200px;
    border-right: 1px solid #E0E0E0;
}

#top-nav {
    position: fixed;
    top: 0;
    left: 200px;
    right: 0;
    height: 66px;
    border-bottom: 1px solid #E0E0E0;
    display: flex;
    justify-content: space-between;
}

#top-nav > div {
    line-height: 66px;
    padding: 0 40px;
}

#logo{
    height: 46px;
    padding: 10px;
    border-bottom: 1px solid #E0E0E0;
}

#main {
    position: fixed;
    top: 66px;
    left: 200px;
    right: 0;
    bottom: 50px;
    background-color: #f2f2f2;
    overflow: auto;
}

#footer {
    height: 50px;
    line-height: 50px;
    width: 100%;
    text-align: center;
    background-color: #ffffff;
    position: absolute;
    bottom: 0;
    left: 200px;
    border-left: #e0e0e0 solid 1px;
    border-top: #e0e0e0 solid 1px;
}

#title {
    font-size: 1rem;
}

#subtitle {
    color: rgba(0, 0, 0, 0.6);
    font-size: 0.875rem;
}
</style>
