<template>
    <div style="padding: 12px">
        <div style="display: flex">
            <el-card style="width: 50%;margin-right: 6px;">
                <div slot="header">系统信息</div>
                <div>
                    <div style="display: flex">
                        <div style="width: 100px;">系统名称：</div>
                        <div>{{ base.os.family }}</div>
                    </div>
                    <div style="display: flex">
                        <div style="width: 100px;">系统版本：</div>
                        <div>{{ base.os.version.codeName }}</div>
                    </div>
                    <div style="display: flex">
                        <div style="width: 100px;">系统版本号：</div>
                        <div>{{ base.os.version.version }}</div>
                    </div>
                    <div style="display: flex">
                        <div style="width: 100px;">内部版本号：</div>
                        <div>{{ base.os.version.buildNumber }}</div>
                    </div>
                    <div style="display: flex">
                        <div style="width: 100px;">系统供应商：</div>
                        <div>{{ base.os.manufacturer }}</div>
                    </div>
                    <div style="display: flex">
                        <div style="width: 100px;">系统位数：</div>
                        <div>{{ base.os.bitness }}</div>
                    </div>
                </div>
            </el-card>
            <el-card style="width: 50%;margin-left: 6px;">
                <div slot="header">
                    <span>状态</span>
                </div>
                <div class="home-card">
                    <div>
                        <div>CPU使用率</div>
                        <el-tooltip placement="top" effect="light">
                            <el-progress type="circle" :percentage="cpu_value" class="home-progress"></el-progress>
                            <span slot="content">
                                <div>{{ dynamic.cpu.cpuModel }} * {{ dynamic.cpu.cpuNum }}</div>
                                <div>已使用：{{ dynamic.cpu.used }}</div>
                                <div>系统：{{ dynamic.cpu.sys }}</div>
                                <div>空闲：{{ dynamic.cpu.free }}</div>
                            </span>
                        </el-tooltip>
                        <div>
                            {{ dynamic.cpu.cpuNum }} 核心
                        </div>
                    </div>
                    <div>
                        <div>内存使用率</div>
                        <el-tooltip placement="top" effect="light">
                            <el-progress type="circle" :percentage="member_value" class="home-progress"></el-progress>
                            <span slot="content">
                                <div>总内存：{{ format(dynamic.memory.total) }}</div>
                                <div>使用中：{{ format(dynamic.memory.total - dynamic.memory.available) }}</div>
                                <div>空闲：{{ format(dynamic.memory.available) }}</div>
                            </span>
                        </el-tooltip>
                        <div>
                            {{ format(dynamic.memory.available) }} / {{ format(dynamic.memory.total) }}
                        </div>
                    </div>
                </div>
            </el-card>
        </div>
        <div style="margin-top: 12px;display: flex">
            <el-card style="width: 50%;margin-rifht: 6px;">
                <div slot="header">概览</div>
                <div class="home-items">
                    <div class="home-item">
                        <div class="home-item-title">数据库</div>
                        <div class="home-item-content">
                            <router-link to="/database" tag="a">{{ base.database }}</router-link>
                        </div>
                    </div>
                    <div class="home-item">
                        <div class="home-item-title">Redis</div>
                        <div class="home-item-content">
                            <router-link to="/redis" tag="a">{{ base.redis }}</router-link>
                        </div>
                    </div>
                </div>
            </el-card>
            <el-card style="width: 50%;margin-left: 6px;">
                <div slot="header">
                    <span>网络</span>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
import { format } from '../../utils/storageUtil';
import { get_dynamic, get_base } from "../../apis/home";


export default {
    name: "home",
    data() {
        return {
            interval: -1,
            base: {
                "os": {
                    "bitness": 64,
                    "version": {
                        "version": "10",
                        "buildNumber": "19042",
                        "codeName": "Home"
                    },
                    "manufacturer": "Microsoft",
                    "family": "Windows"
                },
                "redis": 0,
                "database": 0
            },
            dynamic: {
                "memory": {
                    "available": 2528346112,
                    "total": 16805203968
                },
                "cpu": {
                    "wait": 0,
                    "cpuModel": "Intel(R) Core(TM) i5-10400 CPU @ 2.90GHz",
                    "used": 2.95,
                    "sys": 1.26,
                    "cpuNum": 12,
                    "toTal": 12187,
                    "free": 95.77
                },
            }
        }
    },
    created() {
        this.get_base();
        this.interval = setInterval(() => {
            this.get_dynamic();
        }, 1000);
    },
    computed: {
        cpu_value() {
            return parseFloat((this.dynamic.cpu.used + this.dynamic.cpu.sys).toFixed(2));
        },
        member_value() {
            return parseFloat((100 - this.dynamic.memory.available / this.dynamic.memory.total * 100).toFixed(2))
        }
    },
    methods: {
        format,
        get_base(){
            get_base(res => {
                if (res.success){
                    this.base = res.data.item
                }
            })
        },
        get_dynamic(){
            get_dynamic(res => {
                if (res.success){
                    this.dynamic = res.data.item
                }
            })
        },
    },
    beforeDestroy() {
        clearInterval(this.interval)
    }
}
</script>

<style scoped>
.home-card {
    display: grid;
    grid-template-columns: 1fr 1fr;
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

.home-tip {
    font-size: 12px;
}

.home-items {
    display: flex;
}

.home-item {
    width: 200px;
    background-color: #f2f2f2;
    margin: 0px 12px;
}

.home-item-title {
    width: 100%;
    text-align: center;
    height: 50px;
    line-height: 50px;
    font-size: 15px;
}

.home-item-content {
    width: 100%;
    text-align: center;
    height: 50px;
    font-size: 26px;
}
</style>