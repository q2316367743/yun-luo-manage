import {get, getJSON, post } from '@/utils/axios';

const user_mode = '/user';

export default {
    page(page, size, success, error) {
        get(`${user_mode}/page`, { page, size }, success, error);
    },
    role(success, error) {
        getJSON(`${user_mode}/role`, success, error);
    },
    add(data, success, error) {
        post(`${user_mode}/add`, data, success, error)
    },
    update(id, data, success, error) {
        post(`${user_mode}/update/${id}`, data, success, error)
    },
    reset_password(id, success, error) {
        post(`${user_mode}/reset_password/${id}`, null, success, error)
    },
    remove(id, success, error) {
        post(`${user_mode}/remove/${id}`, null, success, error)
    }
}