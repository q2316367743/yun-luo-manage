import Vue from 'vue'
import Vuex from 'vuex'
import router from './router';

import file from "../pages/file/file.vue";
import server from "../pages/server/server.vue";
import server_info from '@/pages/server/info.vue'
import system_role from '@/pages/system/role/role.vue'
import system_user from '@/pages/system/user/user.vue'

function consist(list, keyword) {
    for (let item of list) {
        if (item.indexOf(keyword) != -1) {
            return true;
        }
    }
    return false;
}

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
    }],
    "role": [{
        path: '/system/role',
        name: 'system_role',
        nickname: '角色管理',
        component: system_role
    }],
    "user": [{
        path: '/system/user',
        name: 'system_user',
        nickname: '用户管理',
        component: system_user
    }]
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
                if (consist(user_permission, route)) {
                    for (let item of router_list[route]) {
                        router.addRoute(item);
                    }
                }
            }
        }
    },
    getters: {
        nickname: state => state.nickname,
        permissions: state => state.permissions
    }
})