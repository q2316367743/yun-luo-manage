import { getJSON, post } from '@/utils/axios';

const role_model = '/role';

export default {
    list(success, error) {
        getJSON(`${role_model}/list`, success, error);
    },
    list_permission(success, error) {
        getJSON(`${role_model}/permissions`, success, error);
    },
    info(id, success, error) {
        getJSON(`${role_model}/info/${id}`, success, error);
    },
    add(data, success, error) {
        post(`${role_model}/add`, data, success, error);
    },
    update(id, data, success, error) {
        post(`${role_model}/update/${id}`, data, success, error);
    },
    remove(id, success, error) {
        post(`${role_model}/remove/${id}`, null, success, error)
    }
}