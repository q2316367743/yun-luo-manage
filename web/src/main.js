import Vue from 'vue'
import App from './App.vue'
import Router from 'vue-router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './plugins/router'

Vue.use(ElementUI);
Vue.use(Router);

Vue.config.productionTip = false;

// 处理路由
const routes = [];
for (let item of router) {
    if (item.children) {
        let path = item.path;
        for (let child of item.children){
            routes.push({
                path: path + '/' + child.path,
                nabla: child.name,
                component: child.component
            })
        }
    }else {
        routes.push({
            path: item.path,
            name: item.name,
            component: item.component
        })
    }
}

new Vue({
    router: new Router({
        routes: routes
    }),
    render: h => h(App)
}).$mount('#app')
