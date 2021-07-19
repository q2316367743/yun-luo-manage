import home from '../pages/home/home.vue';
import database from "../pages/database/database";
import redis from '../pages/redis/redis';
import application from "../pages/application/application";
import file from "../pages/file/file";

import nginx_config from '../pages/nginx/config'

export default [{
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
    path: '/nginx',
    name: 'nginx',
    nickname: 'Nginx管理',
    children: [{
        path: 'config',
        name: 'nginx_config',
        nickname: '配置文件',
        component: nginx_config,
    }]
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
