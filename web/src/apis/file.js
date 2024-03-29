import {get, getJSON, getObject, post, postObject } from '@/utils/axios';

const file_model = 'file';

export default {
    base(success) {
        getJSON(file_model + '/base', success);
    },
    ls(path, success, error) {
        get(file_model + '/ls', { path }, success, error)
    },
    simple_ls(path, success, error) {
        get(file_model + '/simple_ls', { path }, success, error)
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
    open(path, charset, success, error) {
        get(file_model + '/open', { path, charset }, success, error)
    },
    write(path, charset, content, success, error) {
        post(file_model + '/write', { path, charset, content }, success, error)
    },
    show(path, success, error) {
        getObject(file_model + '/show', { path }, success, error);
    },
    download(paths, success, error) {
        let target = paths.join(',');
        getObject(file_model + '/download', { paths: target }, success, error);
    },
    upload(data, success, error) {
        postObject(file_model + '/upload', data, success, error);
    },
    remote_download(path, name, url, success, error) {
        get(file_model + '/remote_download', { path, name, url }, success, error)
    },
    stat(path, success, error) {
        get(`${file_model}/stat`, { path }, success, error)
    }
}