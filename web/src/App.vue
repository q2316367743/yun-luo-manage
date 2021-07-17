<template>
    <v-app>
        <v-navigation-drawer app>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title class="text-h6">
                        云落管理
                    </v-list-item-title>
                    <v-list-item-subtitle>
                        Linux
                    </v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
            <v-divider></v-divider>
            <v-list dense nav>
                <v-list-item link to="/">
                    <v-list-item-content>
                        <v-list-item-title>首页</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link to="/database">
                    <v-list-item-content>
                        <v-list-item-title>数据库</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link to="/redis">
                    <v-list-item-content>
                        <v-list-item-title>Redis</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar app style="background-color: #ffffff">
            <div class="nav">
                <v-menu offset-y open-on-hover>
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            text
                            v-bind="attrs"
                            v-on="on"
                        >
                            管理员
                        </v-btn>
                    </template>
                    <v-list dense>
                        <v-list-item link>
                            <v-list-item-content>
                                <v-list-item-title>退出</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                </v-menu>
            </div>
        </v-app-bar>

        <!-- 根据应用组件来调整你的内容 -->
        <v-main style="background-color: #f2f2f2">

            <!-- 给应用提供合适的间距 -->
            <v-container fluid>

                <!-- 如果使用 vue-router -->
                <router-view></router-view>
            </v-container>
            <div class="footer">云落管理 ©2014-2021 云落天都|建议请上<a href="https://gitee.com/qiaoshengda/yun-luo-manage" target="_blank">码云</a></div>
        </v-main>

    </v-app>
</template>

<script>

export default {
    name: 'App',

    data: () => ({
        //
    }),
};
</script>

<style lang="scss">
.nav {
    text-align: right;
    width: 100%;
    height: 100%;
    line-height: 56px;
}
.footer{
    height: 50px;
    line-height: 50px;
    width: 100%;
    text-align: center;
    background-color: #ffffff;
    margin-top: 12px;
    position: absolute;
    bottom: 0;
}
</style>
