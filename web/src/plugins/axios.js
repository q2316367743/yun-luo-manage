import axios from "axios";
import {Message} from "element-ui";
import router from './router'

const instance = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? './api' : 'http://localhost:7743/api',
    timeout: 3000
})


// http request 拦截器
instance.interceptors.request.use(
    config => {
        // 除了登录，如果没有token，则无法执行。
        /*
        if (sessionStorage.getItem('token')) {
            config.headers['token'] = sessionStorage.getItem('token');
        } else if(config.url !== 'admin/login'){
            throw new axios.Cancel('Operation canceled by the user.');
        }*/
        return config
    },
    err => {
        return Promise.reject(err);
    })

// http response 拦截器
instance.interceptors.response.use(
    response => {
        //debugger
        if (response.data.code === 401) {
            router.push("/login").then(() => {
                Message({
                    message: '您当前暂未登录，请立即登录？',
                    type: 'error',
                    duration: 5 * 1000
                })
            })
            return {
                success: false,
            };
        } else if (response.data.code === 402) {
            router.push("/login").then(() => {
                Message({
                    message: '账户登录过期，请重新登录',
                    type: 'error',
                    duration: 5 * 1000
                })
            })
            sessionStorage.removeItem('token');
            // 返回 错误代码-1 清除ticket信息并跳转到登录页面
            return {
                success: false,
            };
        } else if (response.data.code === 500) {
            return {
                success: false,
            };
        } else {
            return response.data;
        }
    },
    () => {
        return {
            success: false,
        }
    });

export default instance;