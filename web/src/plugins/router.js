import Vue from 'vue'
import Router from 'vue-router'

import home from "../pages/home/home";
import file from "../pages/file/file.vue";
import server from "../pages/server/server.vue";
import database from "../pages/database/database";
import redis from "../pages/redis/redis";
import application from "../pages/application/application";
import login from '@/pages/login/login'

Vue.use(Router);

export default new Router({
    routes: [{
        path: '/',
        name: 'index',
        nickname: '首页',
        component: home
    }, {
        path: '/file',
        name: 'file',
        nickname: '文件管理',
        component: file
    }, {
        path: '/server',
        name: 'server',
        nickname: '服务器管理',
        component: server,
    }, {
        path: '/database',
        name: 'database',
        nickname: '数据库管理',
        component: database
    }, {
        path: '/redis',
        name: 'redis',
        nickname: 'Redis管理',
        component: redis
    }, {
        path: '/application',
        name: 'application',
        nickname: '应用管理',
        component: application
    }, {
        path: '/login',
        name: 'login',
        component: login
    }]
})