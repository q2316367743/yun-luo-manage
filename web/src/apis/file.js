import { get, getJSON, getObject, post, postObject } from '@/utils/axios';

const model = 'file';

export default {
    base(success){
        getJSON(model + '/base', success);
    },
    ls(path, success,error){
        get(model + '/ls', {path}, success, error)
    },
    rename(path, name, target, success){
        post(model + '/rename', {
            path, name, target
        }, success);
    },
    touch(path, name, success){
        post(model + '/touch', {
            path, name
        }, success)
    },
    mkdir(path, name, success){
        post(model + '/mkdir', {
            path, name
        }, success)
    },
    rm(paths, is_force, success){
        post(model + '/rm', {
            paths, is_force
        }, success)
    },
    mv(paths, target, success){
        post(model + '/mv', {
            paths, target
        }, success);
    },
    cp(paths, target, success){
        post(model + '/cp', {
            paths, target
        }, success);
    },
    open(path, charset, success){
        get(model + '/open', {path, charset}, success)
    },
    write(path, charset, content, success, error){
        post(model + '/write', {path, charset, content}, success, error)
    },
    show(path, success, error){
        getObject(model + '/show', {path}, success, error);
    },
    upload(data, success, error){
        postObject(model + '/upload', data, success, error);
    },
    remote_download(path, name, url, success, error){
        get(model + '/remote_download', {path, name, url}, success, error)
    }
}