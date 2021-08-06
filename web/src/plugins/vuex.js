import Vue from 'vue'
import Vuex from 'vuex'
import router from './router';

import file from "../pages/file/file.vue";
import server from "../pages/server/server.vue";
import server_info from '@/pages/server/info.vue'

const router_list = {
    "file": [{
        path: '/file',
        name: 'file',
        nickname: '文件管理',
        component: file
    }],
    'server': [{
        path: '/server',
        name: 'server',
        nickname: '服务器管理',
        component: server,
    }, {
        path: '/server/:id',
        name: 'server_info',
        nickname: '服务器信息',
        component: server_info,
    }]
}

const default_menu = {
    path: '/',
    name: '首页'
}

const menu_list = {
    "file": {
        path: '/file',
        name: '文件管理'
    },
    "server": {
        path: '/server',
        name: '服务器管理'
    }
}

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nickname: '',
        menus: [{
            path: '/',
            name: '首页'
        }]
    },
    mutations: {
        SET_NICKNAME(state, nickname) {
            state.nickname = nickname;
        },
        SET_MENU(state, permissions) {
            state.menus = [];
            state.menus.push(default_menu)
            for (let permission of permissions) {
                for (let route of router_list[permission]) {
                    // 渲染路由
                    router.addRoute(route)
                }
                // 增加菜单
                state.menus.push(menu_list[permission]);
            }
        }
    },
    getters: {
        nickname: state => state.nickname,
        menus: state => state.menus
    }
})