import Vue from 'vue'
import Router from 'vue-router'

import home from '../pages/home/home.vue';
import database from "../pages/database/database";
import redis from '../pages/redis/redis'

Vue.use(Router)

export default new Router({
    routes: [{
        path: '/',
        name: 'index',
        component: home
    }, {
        path: '/database',
        name: 'database',
        component: database
    }, {
        path: '/redis',
        name: 'redis',
        component: redis
    }]
})