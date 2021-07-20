import axios from '../plugins/axios';

export function get_base(success){
    axios({
        url: '/system/base',
        method: 'GET',
    }).then(res => {
        success(res)
    })
}

export function get_dynamic(success){
    axios({
        url: '/system/dynamic',
        method: 'GET',
    }).then(res => {
        success(res)
    })
}