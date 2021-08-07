import Vue from 'vue'
import Vuex from 'vuex'
import router from './router';

import file from "../pages/file/file.vue";
import server from "../pages/server/server.vue";
import server_info from '@/pages/server/info.vue'
import system_role from '@/pages/system/role/role.vue'
import system_user from '@/pages/system/user/user.vue'

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

const default_menu = {
    path: '/',
    name: '首页'
}

const menu_list = {
    file: {
        name: '文件管理',
        children: {
            "file": {
                path: '/file',
                name: '文件管理'
            }
        }
    },
    server: {
        name: '服务器管理',
        children: {
            "server": {
                path: '/server',
                name: '服务器管理'
            }
        }
    },
    system: {
        name: '系统管理',
        children: {
            role: {
                name: "角色管理",
                path: "/system/role"
            },
            user: {
                name: "用户管理",
                path: "/system/user"
            }
        }
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
        SET_MENU(state, user_permission) {
            state.menus = [];
            state.menus.push(default_menu)
                // 获取分类
            for (let category in user_permission) {
                // 获取分类下权限列表
                for (let permission of user_permission[category]) {
                    // 获取每个权限
                    for (let route of router_list[permission]) {
                        // 渲染路由
                        router.addRoute(route)
                    }

                }
                // 根据大的分类获取下面菜单列表
                let categorys = menu_list[category];
                let permissions = user_permission[category];
                if (Object.keys(categorys.children).length > 1) {
                    // 多级菜单
                    let temp = {
                        name: categorys.name,
                        children: []
                    };
                    for (let permission of permissions) {
                        temp.children.push(categorys.children[permission]);
                    }
                    state.menus.push(temp);
                } else {
                    // 单独一个，一级菜单
                    let temp = {
                        name: categorys.name,
                        path: categorys.children[category].path
                    };
                    state.menus.push(temp);
                }
            }
        }
    },
    getters: {
        nickname: state => state.nickname,
        menus: state => state.menus
    }
})