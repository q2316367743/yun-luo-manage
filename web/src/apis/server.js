import { getJSON, post } from '@/utils/axios';

const server_model = "/server"

export default {
    list(success, error) {
        getJSON(server_model + '/list', success, error);
    },
    info(id, success, error) {
        getJSON(`${server_model}/info/${id}`, success, error);
    },
    add(data, success, error) {
        post(server_model + '/add', data, success, error);
    },
    update(id, data, success, error) {
        post(`${server_model}/update/${id}`, data, success, error);
    },
    remove(id, success, error) {
        post(`${server_model}/remove/${id}`, null, success, error);
    },
    command_add(data, success, error) {
        post(`${server_model}/command/add`, data, success, error);
    },
    command_update(id, data, success, error) {
        post(`${server_model}/command/update/${id}`, data, success, error);
    },
    command_remove(id, success, error) {
        post(`${server_model}/command/remove/${id}`, null, success, error);
    },
    config_add(data, success, error) {
        post(`${server_model}/config/add`, data, success, error);
    },
    config_update(id, data, success, error) {
        post(`${server_model}/config/update/${id}`, data, success, error);
    },
    config_remove(id, success, error) {
        post(`${server_model}/config/remove/${id}`, null, success, error);
    },
    config_write(path, charset, content, success, error) {
        post(`${server_model}/config/write`, { path, charset, content }, success, error)
    },
    command_exec(id, success, error) {
        getJSON(`${server_model}/command/exec/${id}`, success, error);
    }
}