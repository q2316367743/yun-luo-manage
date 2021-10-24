// 初始化脚本
import '@/plugins/init'
import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from "./plugins/router";
import store from "./plugins/vuex";

import './main.css'

Vue.prototype.$layer = window.layer
Vue.use(ElementUI);

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')