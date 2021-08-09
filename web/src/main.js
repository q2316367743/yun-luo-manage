import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/plugins/layer.js'
import router from "./plugins/router";
import store from "./plugins/vuex";

import './main.css'
import './plugins/layer'

Vue.use(ElementUI);

Vue.config.productionTip = false;

// 初始化字典
Vue.prototype.$dict = {
    server_type: {
        '自定义服务器': 0,
        'Nginx': 1,
        'Apache': 2,
        'Tomcat': 3
    }
}

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')