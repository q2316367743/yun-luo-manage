<template>
    <div class="db-main" @click="old_choose()">
        <el-card style="height: 100%;position: relative">
            <div class="db-list">
                <div class="db-list-option"></div>
                <!-- 全部数据库列表 -->
                <div v-for="(database, index) in databases" :key="index">
                    <div class="db-database" :class="{'db-list-choose': choose_id === database.id, 'db-list-old-choose': old_choose_id === database.id}" @click.stop="choose(database.id)">{{ database.nickname }}</div>
                </div>
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
            databases: [{
                id: '111',
                nickname: 'root@localhost',
                children: [{
                    name: 'table'
                }, {
                    name: 'view'
                }]
            }, {
                id: '222',
                nickname: 'root@localhost',
                children: [{
                    name: 'table'
                }, {
                    name: 'view'
                }]
            }],
            choose_id: '111',
            old_choose_id: '',
        }
    },
    methods: {
        choose(id){
            this.old_choose_id = '';
            this.choose_id = id;
        },
        old_choose(){
            if (this.choose_id !== ''){
                this.old_choose_id = this.choose_id;
                this.choose_id = ''
            }
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
    overflow-x: hidden;
    overflow-y: auto;
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

.db-list-option{

}

.db-list-choose{
    background-color: #409EFF;
}

.db-list-old-choose{
    background-color: #f2f2f2;
}

.db-database{
    height: 16px;
    font-size: 12px;
    padding: 6px 16px;
    cursor: pointer;
}

</style>