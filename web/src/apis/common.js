import {get, getJSON, post } from '@/utils/axios';

const common_model = '/common';

export function login(username, password, success, error) {
    get(common_model + '/login', { username, password }, success, error);
}

export function logout(success) {
    getJSON(common_model + '/logout', success);
}

export function update(username, old, password, success, error) {
    post(common_model + '/update', { username, old, password }, success, error);
}

export function info(success) {
    getJSON(`${common_model}/info`, success)
}