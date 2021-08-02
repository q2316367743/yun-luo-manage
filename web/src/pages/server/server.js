// 引入API
import server_api from '@/apis/server.js';

export default {
    name: "server",
    data: () => ({
        servers: [],
        server: {
            name: '',
            type: '',
            version: ''
        },
        server_dialog: false,
        server_update: false
    }),
    created() {
        this.get_server_list();
    },
    methods: {
        get_server_list() {
            server_api.list(res => {
                if (res.success) {
                    this.servers = res.data.items;
                }
            })
        },
        open_add_server() {
            this.server = {
                name: '',
                type: '',
                version: ''
            };
            this.server_update = false;
            this.server_dialog = true;
        },
        server_dialog_submit() {
            if (!this.server_update) {
                // 增加
                server_api.add(this.server, res => {
                    if (res.success) {
                        this.$message.success('新增服务器成功');
                        this.get_server_list();
                    }
                }, () => {
                    this.$message.error('新增服务器失败');
                })
            }
            this.server_dialog = false;
        },
        install_server(type) {
            this.$message.success("安装服务器，类型：" + type);
        },
        open_info(id) {
            this.$router.push(`/server/${id}`);
        },
        remove(id) {
            this.$confirm('此操作将永久删除该服务器, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                server_api.remove(id, res => {
                    if (res.success) {
                        this.$message.success('删除成功');
                        this.get_server_list();
                    }
                }, (message) => {
                    this.$message.error('删除失败，' + message);
                });
            })

        }
    }
};