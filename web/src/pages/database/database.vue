<template>
    <div class="db-main">
        <el-card style="height: 100%;position: relative">
            <div class="db-list">
                <!-- 全部数据库列表 -->
                <el-tree :data="databases" :props="list_prop" style="margin-top: 12px">
                    <span class="custom-tree-node" slot-scope="{ node, data }">
                        <span>{{ data.name }}</span>
                    </span>
                </el-tree>
            </div>
            <div class="db-content">
                <div class="db-show">
                    <div class="db-show-tab">
                        <el-tabs closable>
                            <el-tab-pane label="root@localhost"></el-tab-pane>
                            <el-tab-pane label="root@localhost"></el-tab-pane>
                        </el-tabs>
                    </div>
                </div>
                <div class="db-console">
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: "database",
    data() {
        return {
            list_prop: {
                children: 'children',
                label: 'name'
            },
            databases: [{
                name: 'root@localhost',
                children: [{
                    name: 'table'
                }, {
                    name: 'view'
                }]
            }]
        }
    }
}
</script>

<style scoped>
.db-main {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    padding: 12px;
}

.db-list {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 250px;
    border: 1px solid #E0E0E0;
    border-radius: 5px;
}

.db-content {
    position: absolute;
    top: 0;
    left: 250px;
    bottom: 0;
    right: 0;
}

.db-show {
    border: 1px solid #E0E0E0;
    border-radius: 5px;
    position: absolute;
    top: 0;
    left: 0;
    bottom: 250px;
    right: 0;
    padding: 12px;
}

.db-show-tab {
    position: absolute;
    top: 12px;
    left: 12px;
    right: 12px;
}

.db-console {
    border: 1px solid #E0E0E0;
    border-radius: 5px;
    height: 250px;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
}
</style>