<template>
    <div id="app">
        <div id="left-nav">
            <div id="logo">
                <div id="title">云落管理</div>
                <div id="subtitle">Linux</div>
            </div>
            <el-menu
                :default-active="index"
                background-color="#2B2D41"
                text-color="#ffffff"
                router>
                <el-menu-item index="/">主页</el-menu-item>
                <el-menu-item index="/file">文件管理</el-menu-item>
                <el-submenu index="">
                    <template slot="title">Nginx</template>
                    <el-menu-item index="/nginx/config">配置文件</el-menu-item>
                </el-submenu>
                <el-menu-item index="/database">数据库管理</el-menu-item>
                <el-menu-item index="/redis">Redis管理</el-menu-item>
                <el-menu-item index="/application">应用管理</el-menu-item>
            </el-menu>
        </div>

        <div id="top-nav">
            <div></div>
            <div>
                <el-menu mode="horizontal"
                    background-color="#212334"
                    text-color="#ffffff"
                    active-text-color="#ffffff">
                    <el-submenu index="">
                        <template slot="title">管理员</template>
                        <el-menu-item index="">退出</el-menu-item>
                    </el-submenu>
                </el-menu>
            </div>
        </div>

        <!-- 根据应用组件来调整你的内容 -->
        <div id="main">
            <router-view></router-view>
        </div>

    </div>
</template>

<script>

export default {
    name: 'App',

    data: () => ({
        index: '/',
    }),
    watch: {
        $route(to) {
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
    background-color: #2B2D41;
}

#top-nav {
    position: fixed;
    top: 0;
    left: 200px;
    right: 0;
    height: 66px;
    display: flex;
    justify-content: space-between;
    background-color: #212334;
    color: #ffffff;
}

#top-nav > div {
    line-height: 66px;
    padding: 0 40px;
}

#logo {
    height: 46px;
    padding: 10px;
    color: #ffffff;
}

#main {
    position: fixed;
    top: 66px;
    left: 200px;
    right: 0;
    bottom: 0;
    background-color: #212334;
    overflow: auto;
}


#title {
    font-size: 1rem;
}

#subtitle {
    color: #f2f2f2;
    font-size: 0.875rem;
}
.el-menu{
    border-right: solid 1px #2B2D41 !important;
}
.el-card{
    background-color: #2A2E3F !important;
    color: #ffffff !important;
    border: #2A2E3F !important;
}
.el-card__header{
    border-bottom: 1px solid #212334 !important;
}
.el-menu.el-menu--horizontal{
    border: #2A2E3F !important;
}
.el-submenu__title{
    border-bottom: 2px solid #212334 !important;
}
.el-form-item__label{
    color: #ffffff !important;
}
.el-tabs__item{
    color: #ffffff !important;
}
</style>
