import {get, getJSON, getObject, post, postObject } from '@/utils/axios';

const file_model = 'file';

export default {
    base(success) {
        getJSON(file_model + '/base', success);
    },
    ls(path, success, error) {
        get(file_model + '/ls', { path }, success, error)
    },
    rename(path, name, target, success) {
        post(file_model + '/rename', {
            path,
            name,
            target
        }, success);
    },
    touch(path, name, success) {
        post(file_model + '/touch', {
            path,
            name
        }, success)
    },
    mkdir(path, name, success) {
        post(file_model + '/mkdir', {
            path,
            name
        }, success)
    },
    rm(paths, is_force, success) {
        post(file_model + '/rm', {
            paths,
            is_force
        }, success)
    },
    mv(paths, target, success) {
        post(file_model + '/mv', {
            paths,
            target
        }, success);
    },
    cp(paths, target, success) {
        post(file_model + '/cp', {
            paths,
            target
        }, success);
    },
    open(path, charset, success) {
        get(file_model + '/open', { path, charset }, success)
    },
    write(path, charset, content, success, error) {
        post(file_model + '/write', { path, charset, content }, success, error)
    },
    show(path, success, error) {
        getObject(file_model + '/show', { path }, success, error);
    },
    upload(data, success, error) {
        postObject(file_model + '/upload', data, success, error);
    },
    remote_download(path, name, url, success, error) {
        get(file_model + '/remote_download', { path, name, url }, success, error)
    }
}