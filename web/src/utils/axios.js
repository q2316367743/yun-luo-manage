import axios from '../plugins/axios';
import { Message } from 'element-ui';

/**
 * 执行无参get请求
 * 
 * @param {String} url 访问的URL
 * @param {function} success 成功回调
 * @param {function} error 失败回调
 */
export function getJSON(url, success, error) {
    axios({
        url,
        method: 'GET'
    }).then(res => {
        if (res.success) {
            success(res)
        } else {
            if (error) {
                error(res.message);
            }
            success({
                success: false
            })
        }
    }).catch(() => {
        if (error) {
            error('服务器错误');
        } else {
            Message({
                message: '服务器错误',
                type: 'error'
            })
        }
    })
}

export function getObject(url, params, success, error) {
    axios({
        url,
        method: 'GET',
        params,
        responseType: 'blob'
    }).then(res => {
        if (res.success) {
            success(res)
        } else {
            if (error) {
                error(res.message);
            }
            success({
                success: false
            })
        }
    }).catch((err) => {
        console.error(err)
        if (error) {
            error();
        } else {
            Message({
                message: '服务器错误',
                type: 'error'
            })
        }
    })
}

/**
 * 执行get请求
 * 
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {Function} success 成功回调
 * @param {Function} error 失败回调
 */
export function get(url, params, success, error) {
    axios({
        url,
        method: 'GET',
        params
    }).then(res => {
        if (res.success) {
            success(res)
        } else {
            if (error) {
                error(res.message);
            } else {
                Message({
                    message: res.message,
                    type: 'error'
                })
            }
            success({
                success: false
            })
        }
    }).catch(() => {
        if (error) {
            error('服务器错误');
        } else {
            Message({
                message: '服务器错误',
                type: 'error'
            })
        }
    })
}

/**
 * 执行post请求
 * 
 * @param {String} url 请求地址
 * @param {Object} data 请求参数
 * @param {Function} success 成功回调
 * @param {Function} error 失败回调
 */
export function post(url, data, success, error) {
    axios({
        url,
        method: 'POST',
        data
    }).then(res => {
        if (res.success) {
            success(res)
        } else {
            if (error) {
                error(res.message);
            }
            success({
                success: false
            })
        }
    }).catch(() => {
        if (error) {
            error('服务器错误');
        } else {
            Message({
                message: '服务器错误',
                type: 'error'
            })
        }
    })
}

/**
 * 执行post请求，用户上传
 * 
 * @param {String} url 请求地址
 * @param {Object} data 请求参数
 * @param {Function} success 成功回调
 * @param {Function} error 失败回调
 */
export function postObject(url, data, success, error) {
    axios({
        url,
        method: 'POST',
        data,
        headers: {
            "Content-type": "multipart/form-data"
        }
    }).then(res => {
        if (res.success) {
            success(res)
        } else {
            if (error) {
                error(res.message);
            }
            success({
                success: false
            })
        }
    }).catch(() => {
        if (error) {
            error('服务器错误');
        } else {
            Message({
                message: '服务器错误',
                type: 'error'
            })
        }
    })
}