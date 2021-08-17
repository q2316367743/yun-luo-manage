import Vue from 'vue'
import Vuex from 'vuex'
import router from './router';

import file from "../pages/file/file.vue";
import server from "../pages/server/own/server.vue";
import server_info from '@/pages/server/own/info.vue'
import system_role from '@/pages/system/role/role.vue'
import system_user from '@/pages/system/user/user.vue'

const router_list = {
    "file&a": {
        path: '/file',
        name: 'file',
        nickname: '文件管理',
        component: file
    },
    'server-own&l': {
        path: '/server/own',
        name: 'server',
        nickname: '服务器管理',
        component: server,
    },
    "server-own&i": {
        path: '/server/own/:id',
        name: 'server_info',
        nickname: '服务器信息',
        component: server_info,
    },
    "system-role&l": {
        path: '/system/role',
        name: 'system_role',
        nickname: '角色管理',
        component: system_role
    },
    "system-user&l": {
        path: '/system/user',
        name: 'system_user',
        nickname: '用户管理',
        component: system_user
    }
}

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nickname: '',
        permissions: [],
    },
    mutations: {
        SET_NICKNAME(state, nickname) {
            state.nickname = nickname;
        },
        SET_MENU(state, user_permission) {
            state.permissions = user_permission;

            //router.addRoute(route)

            for (let route in router_list) {
                if (user_permission.consists(route)) {
                    router.addRoute(router_list[route]);
                }
            }
        }
    },
    getters: {
        nickname: state => state.nickname,
        permissions: state => state.permissions
    }
})