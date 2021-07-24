import {getJSON} from '@/utils/axios';

export function get_base(success){
    getJSON('/system/base', success);
}

export function get_dynamic(success){
    getJSON('/system/dynamic', success);
}