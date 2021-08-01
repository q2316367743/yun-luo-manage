<template>
	<div>
		<el-tabs tab-position="left">
			<el-tab-pane label="基础信息">
				<el-form label-position="right" label-width="120px">
					<el-form-item label="名称：">
						<el-input
							v-model="server.name"
							v-if="is_update"
						></el-input>
						<div v-else>{{ server.name }}</div>
					</el-form-item>
					<el-form-item label="类型：">
						<el-select
							v-model="server.type"
							placeholder="请选择"
							v-if="is_update"
						>
							<el-option
								v-for="(value, key) in $dict.server_type"
								:key="value"
								:label="key"
								:value="value"
							>
							</el-option>
						</el-select>
						<div v-else>{{ getTypeByValue(server.type) }}</div>
					</el-form-item>
					<el-form-item label="类型名称：">
						<el-input
							v-model="server.type_name"
							:disabled="server.type !== 0"
							v-if="is_update"
						></el-input>
						<div v-else>{{ server.type_name }}</div>
					</el-form-item>
					<el-form-item label="版本：">
						<el-input
							v-model="server.version"
							v-if="is_update"
						></el-input>
						<div v-else>{{ server.version }}</div>
					</el-form-item>
					<el-form-item>
						<el-button @click="is_update = !is_update">{{
							is_update ? "取消" : "修改"
						}}</el-button>
						<el-button @click="reset" v-if="is_update"
							>重置</el-button
						>
						<el-button
							type="primary"
							@click="update"
							v-if="is_update"
							>修改</el-button
						>
					</el-form-item>
				</el-form>
			</el-tab-pane>
			<el-tab-pane label="操作命令">
				<el-table :data="commands" style="width: 100%">
					<el-table-column label="序号" type="index" width="180">
					</el-table-column>
					<el-table-column prop="name" label="名称"> </el-table-column
					><el-table-column prop="command" label="命令">
					</el-table-column
					><el-table-column label="操作">
						<el-button type="text">执行</el-button>
						<el-button type="text">修改</el-button>
						<el-button type="text">删除</el-button></el-table-column
					></el-table
				>
			</el-tab-pane>
			<el-tab-pane label="配置文件">
				<el-table :data="configs" style="width: 100%">
					<el-table-column label="序号" type="index" width="180">
					</el-table-column>
					><el-table-column prop="path" label="文件位置">
					</el-table-column
					><el-table-column label="操作">
						<el-button type="text">修改</el-button>
						<el-button type="text">删除</el-button></el-table-column
					></el-table
				>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
import server_api from "@/apis/server";
export default {
	name: "info",
	props: {
		id: String,
	},
	data: () => ({
		is_update: false,
		server: {
			name: "",
			type: 0,
			type_name: "",
			version: "",
		},
		server_default: {
			name: "",
			type: 0,
			type_name: "",
			version: "",
			commands: [],
			configs: [],
		},
		commands: [],
		configs: [],
	}),
	created() {
		this.get_server_info();
	},
	methods: {
		get_server_info() {
			// 获取信息
			server_api.info(this.id, (res) => {
				if (res.success) {
					this.server_default = res.data.item;
					this.server.name = this.server_default.name;
					this.server.type = this.server_default.type;
					this.server.type_name = this.server_default.type_name;
					this.server.version = this.server_default.version;
					this.commands = this.server_default.commands;
					this.configs = this.server_default.configs;
				}
			});
		},
		getTypeByValue(type) {
			for (let item in this.$dict.server_type) {
				if (this.$dict.server_type[item] === type) {
					return item;
				}
			}
			return "";
		},
		reset() {
			this.server.name = this.server_default.name;
			this.server.type = this.server_default.type;
			this.server.type_name = this.server_default.type_name;
			this.server.version = this.server_default.version;
		},
		update() {
			server_api.update(this.id, this.server, (res) => {
				if (res.success) {
					this.$message.success("修改成功");
					this.get_server_info();
				}
			});
		},
	},
};
</script>

<style>
</style>