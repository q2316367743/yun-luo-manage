<template>
    <el-tree class="explore" :props="props" :load="load_node" lazy @node-click="node_click">
    </el-tree>
</template>

<script>
import file_api from '@/apis/file';

export default {
    name: 'file-explore',
    data: () => ({
        props: {
            label: 'name',
            isLeaf: 'is_directory'
        },
        path: '/',
        is_directory: false,
    }),
    methods: {
        get_path() {
            return this.path;
        },
        get_is_directory() {
            return this.is_directory;
        },
        load_node(node, resolve) {
            if (node.level === 0) {
                resolve([{
                    name: '/',
                    path: '/',
                    isLeaf: false,
                    children: []
                }])
            } else {
                file_api.simple_ls(node.data.path, (res) => {
                    if (res.success) {
                        let list = [];
                        for (let item of res.data.items) {
                            list.push({
                                name: item.name,
                                path: item.path,
                                isLeaf: !item.is_directory,
                            })
                        }
                        resolve(list)
                    }
                }, () => {
                    resolve([])
                })
            }

        },
        node_click(node) {
            this.path = node.path;
            this.is_directory = !node.isLeaf;
        }
    }
}
</script>

<style scoped>
.explore {
    max-height: 60vh;
    overflow-y: auto;
}
</style>
