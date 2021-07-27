import {get, getJSON } from '@/utils/axios';

const user_model = '/user';

export function login(username, password, success, error) {
    get(user_model + '/login', { username, password }, success, error);
}

export function logout(success) {
    getJSON(user_model + '/logout', success);
}