<template>
    <div>
        <!-- 顶部数据 -->
        <v-card>
            <v-card-title>状态</v-card-title>
            <v-card-text>
                <div class="home-card">
                    <div>
                        <div>CPU使用率</div>
                        <v-tooltip top>
                            <template v-slot:activator="{ on, attrs }">
                                <v-progress-circular
                                    :rotate="-90"
                                    :size="100"
                                    :width="8"
                                    :value="20"
                                    color="#26c6da"
                                    class="home-progress"
                                    v-bind="attrs"
                                    v-on="on">
                                    20%
                                </v-progress-circular>
                            </template>
                            <div class="home-tip">
                                <div>Intel(R) Xeon(R) CPU E5-2680 v2 @ 2.80GHz * 2</div>
                                <div>2个物理核心，2个物理CPU，2线程</div>
                            </div>
                        </v-tooltip>
                        <div>
                            2 核心
                        </div>
                    </div>
                    <div>
                        <div>CPU使用率</div>
                        <v-tooltip top>
                            <template v-slot:activator="{ on, attrs }">
                                <v-progress-circular
                                    :rotate="-90"
                                    :size="100"
                                    :width="8"
                                    :value="20"
                                    color="#26c6da"
                                    class="home-progress"
                                    v-bind="attrs"
                                    v-on="on">
                                    20%
                                </v-progress-circular>
                            </template>
                            <div class="home-tip">
                                <div>Intel(R) Xeon(R) CPU E5-2680 v2 @ 2.80GHz * 2</div>
                                <div>2个物理核心，2个物理CPU，2线程</div>
                            </div>
                        </v-tooltip>
                        <div>
                            2 核心
                        </div>
                    </div>
                    <div>
                        <div>CPU使用率</div>
                        <v-tooltip top>
                            <template v-slot:activator="{ on, attrs }">
                                <v-progress-circular
                                    :rotate="-90"
                                    :size="100"
                                    :width="8"
                                    :value="20"
                                    color="#26c6da"
                                    class="home-progress"
                                    v-bind="attrs"
                                    v-on="on">
                                    20%
                                </v-progress-circular>
                            </template>
                            <div class="home-tip">
                                <div>Intel(R) Xeon(R) CPU E5-2680 v2 @ 2.80GHz * 2</div>
                                <div>2个物理核心，2个物理CPU，2线程</div>
                            </div>
                        </v-tooltip>
                        <div>
                            2 核心
                        </div>
                    </div>
                    <div>
                        <div>CPU使用率</div>
                        <v-tooltip top>
                            <template v-slot:activator="{ on, attrs }">
                                <v-progress-circular
                                    :rotate="-90"
                                    :size="100"
                                    :width="8"
                                    :value="20"
                                    color="#26c6da"
                                    class="home-progress"
                                    v-bind="attrs"
                                    v-on="on">
                                    20%
                                </v-progress-circular>
                            </template>
                            <div class="home-tip">
                                <div>Intel(R) Xeon(R) CPU E5-2680 v2 @ 2.80GHz * 2</div>
                                <div>2个物理核心，2个物理CPU，2线程</div>
                            </div>
                        </v-tooltip>
                        <div>
                            2 核心
                        </div>
                    </div>
                </div>
            </v-card-text>
        </v-card>
        <v-card style="margin-top: 12px">
            <v-card-title>概览</v-card-title>
            <v-card-text>
                <div class="home-items">
                    <div class="home-item">
                        <div class="home-item-title">数据库</div>
                        <div class="home-item-content"><router-link to="/database" tag="a">0</router-link></div>
                    </div>
                    <div class="home-item">
                        <div class="home-item-title">Redis</div>
                        <div class="home-item-content"><router-link to="/redis" tag="a">0</router-link></div>
                    </div>
                </div>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
export default {
    name: "home"
}
</script>

<style scoped>
.home-card {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 1fr;
}

.home-card > div {
    text-align: center;
}

.home-progress {
    margin-top: 10px;
    font-size: 24px;
    margin-bottom: 10px;
}

.home-tip{
    font-size: 12px;
}

.home-items{
    display: flex;
}
.home-item{
    width: 200px;
    background-color: #f2f2f2;
    margin: 0px 12px;
}
.home-item-title{
    width: 100%;
    text-align: center;
    height: 50px;
    line-height: 50px;
    font-size: 15px;
}
.home-item-content{
    width: 100%;
    text-align: center;
    height: 50px;
    font-size: 26px;
}
</style>