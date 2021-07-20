import Vue from 'vue'
import Router from 'vue-router'

import home from "../pages/home/home";
import file from "../pages/file/file";
import nginx_config from "../pages/nginx/config";
import database from "../pages/database/database";
import redis from "../pages/redis/redis";
import application from "../pages/application/application";

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
        path: '/nginx/config',
        name: 'nginx_config',
        nickname: 'Nginx管理 - 配置文件',
        component: nginx_config,
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
    }]
})