import { getJSON } from '@/utils/axios';

export function get_base(success) {
    getJSON('/home/base', success);
}

export function get_dynamic(success) {
    getJSON('/home/dynamic', success);
}