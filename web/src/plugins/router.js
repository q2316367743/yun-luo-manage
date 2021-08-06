import Vue from 'vue'
import Router from 'vue-router'

import home from "@/pages/home/home";
import login from '@/pages/login/login'

Vue.use(Router);

export default new Router({
    routes: [{
        path: '/',
        name: 'index',
        nickname: '首页',
        component: home
    }, {
        path: '/login',
        name: 'login',
        component: login
    }]
})