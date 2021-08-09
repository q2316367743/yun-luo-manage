<template>
	<el-card class="server-info">
		<el-page-header @back="go_back" content="服务器详情"></el-page-header>
		<el-tabs tab-position="top" style="margin-top: 52px" v-model="index">
			<el-tab-pane label="基础信息" name="base">
				<el-form
					label-position="right"
					label-width="70px"
					style="margin-top: 22px"
				>
					<el-form-item label="名称：">
						<el-input
							v-model="server.name"
							v-if="is_update"
							style="width: 400px"
						></el-input>
						<div v-else>{{ server.name }}</div>
					</el-form-item>
					<el-form-item label="类型：">
						<el-input
							v-model="server.type"
							v-if="is_update"
							style="width: 400px"
						></el-input>
						<div v-else>{{ server.type }}</div>
					</el-form-item>
					<el-form-item label="版本：">
						<el-input
							v-model="server.version"
							style="width: 400px"
							v-if="is_update"
						></el-input>
						<div v-else>{{ server.version }}</div>
					</el-form-item>
					<el-form-item>
						<el-button @click="is_update = !is_update"
							>{{ is_update ? "取消" : "修改" }}
						</el-button>
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
			<el-tab-pane label="操作命令" name="command">
				<el-table
					:data="commands"
					style="width: 100%; margin-top: 22px"
				>
					<el-table-column label="序号" type="index" width="180">
					</el-table-column>
					<el-table-column prop="name" label="名称">
					</el-table-column>
					<el-table-column prop="command" label="命令">
					</el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button
								type="text"
								@click="command_exec(scope.row.id)"
								>执行</el-button
							>
							<el-button
								type="text"
								@click="open_command_dialog(true, scope.row)"
								>修改</el-button
							>
							<el-button
								type="text"
								@click="command_remove(scope.row.id)"
								>删除</el-button
							>
						</template>
					</el-table-column>
				</el-table>
			</el-tab-pane>
			<el-tab-pane label="配置文件" name="config">
				<el-table :data="configs" style="width: 100%; margin-top: 22px">
					<el-table-column label="序号" type="index" width="180">
					</el-table-column>
					>
					<el-table-column prop="path" label="文件位置">
					</el-table-column>
					<el-table-column label="操作" width="180">
						<template slot-scope="scope">
							<el-button type="text">修改</el-button>
							<el-button
								type="text"
								@click="config_remove(scope.row.id)"
								>删除</el-button
							>
						</template>
					</el-table-column>
				</el-table>
			</el-tab-pane>
		</el-tabs>
		<el-dialog
			:title="command_is_update ? '修改' : '新增' + '命令'"
			:visible.sync="command_dialog"
			v-if="command_dialog"
			destroy-on-close
			append-to-body
			:modal-append-to-body="false"
			:close-on-click-modal="false"
			width="50%"
		>
			<el-form label-position="right" label-width="120px">
				<el-form-item label="名称：">
					<el-input v-model="command.name"></el-input>
				</el-form-item>
				<el-form-item label="命令：">
					<el-input v-model="command.command"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="command_dialog = false">取 消</el-button>
				<el-button type="primary" @click="command_dialog_submit">{{
					command_is_update ? "修 改" : "新 增"
				}}</el-button>
			</span>
		</el-dialog>
		<el-dialog
			:title="config_is_update ? '修改' : '新增' + '配置文件'"
			:visible.sync="config_dialog"
			v-if="config_dialog"
			destroy-on-close
			append-to-body
			:modal-append-to-body="false"
			:close-on-click-modal="false"
			width="50%"
			top="10vh"
		>
			<file-explore ref="explore"></file-explore>
			<span slot="footer" class="dialog-footer">
				<el-button @click="config_dialog = false">取 消</el-button>
				<el-button type="primary" @click="config_dialog_submit">{{
					config_is_update ? "修 改" : "新 增"
				}}</el-button>
			</span>
		</el-dialog>
		<div
			class="add_button"
			v-if="index === 'command' || index === 'config'"
		>
			<el-button
				v-if="index === 'command'"
				icon="el-icon-plus"
				circle
				@click="open_command_dialog(false)"
				style="font-size: 34px"
			></el-button>
			<el-button
				v-if="index === 'config'"
				icon="el-icon-plus"
				circle
				@click="open_config_dialog(false)"
				style="font-size: 34px"
			></el-button>
		</div>
	</el-card>
</template>

<script>
import server_api from "@/apis/server";
import file_explore from "@/components/file_explore";
export default {
	name: "info",
	components: {
		"file-explore": file_explore,
	},
	data: () => ({
		id: -1,
		is_update: false,
		index: "base",
		server: {
			name: "",
			type: "",
			version: "",
		},
		server_default: {
			name: "",
			type: "",
			version: "",
			commands: [],
			configs: [],
		},
		commands: [],
		configs: [],
		command_dialog: false,
		command_is_update: false,
		command: {
			name: "",
			command: "",
		},
		config_dialog: false,
		config_is_update: false,
	}),
	created() {
		this.id = this.$route.params.id;
		this.get_server_info();
	},
	methods: {
		go_back() {
			this.$router.push("/server");
		},
		get_server_info() {
			// 获取信息
			server_api.info(this.id, (res) => {
				if (res.success) {
					this.server_default = res.data.item;
					this.server.name = this.server_default.name;
					this.server.type = this.server_default.type;
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
		open_command_dialog(command_is_update, command) {
			this.command_dialog = true;
			this.command_is_update = command_is_update;
			if (command_is_update) {
				this.command = command;
			} else {
				this.command = {
					name: "",
					command: "",
				};
			}
		},
		command_dialog_submit() {
			this.command_dialog = false;
			if (this.command_is_update) {
				server_api.command_update(
					this.command.id,
					{
						name: this.command.name,
						command: this.command.command,
					},
					(res) => {
						if (res.success) {
							this.$message.success("修改命令成功");
							this.get_server_info();
							this.command = {
								name: "",
								command: "",
							};
						}
					},
					(message) => {
						this.$message.error("修改命令失败，" + message);
					}
				);
			} else {
				server_api.command_add(
					{
						name: this.command.name,
						command: this.command.command,
						server_id: this.id,
					},
					(res) => {
						if (res.success) {
							this.$message.success("新增命令成功");
							this.get_server_info();
							this.command = {
								name: "",
								command: "",
							};
						}
					},
					(message) => {
						this.$message.error("新增命令失败，" + message);
					}
				);
			}
		},
		command_remove(id) {
			this.$confirm("此操作将永久删除该命令, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				server_api.command_remove(
					id,
					(res) => {
						if (res.success) {
							this.$message({
								type: "success",
								message: "删除成功!",
							});
							this.get_server_info();
						}
					},
					(message) => {
						this.$message.error("删除命令失败，" + message);
					}
				);
			});
		},
		open_config_dialog(config_is_update) {
			this.config_dialog = true;
			this.config_is_update = config_is_update;
		},
		config_dialog_submit() {
			let explore = this.$refs.explore;
			if (!explore.get_is_directory()) {
				server_api.config_add(
					{
						path: explore.get_path(),
						server_id: this.id,
					},
					(res) => {
						if (res.success) {
							this.config_dialog = false;
							this.$message.success("新增配置文件成功");
							this.get_server_info();
						}
					},
					(message) => {
						this.$message.error("新增配置文件失败，" + message);
					}
				);
			} else {
				this.$message.error("请选择文件！");
			}
		},
		config_remove(id) {
			this.$confirm("此操作将永久删除该配置文件, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				server_api.config_remove(
					id,
					(res) => {
						if (res.success) {
							this.$message({
								type: "success",
								message: "删除成功!",
							});
							this.get_server_info();
						}
					},
					(message) => {
						this.$message.error("删除配置文件失败，" + message);
					}
				);
			});
		},
		command_exec(id) {
			server_api.command_exec(
				id,
				(res) => {
					if (res.success) {
						console.log(res.data.item);
						console.log(res.data.item.split("\n").join("<br />"));
						this.$msgbox({
							title: "执行结果",
							message: res.data.item.split("\n").join("<br />"),
							closeOnClickModal: false,
							dangerouslyUseHTMLString: true,
						});
					}
				},
				(message) => {
					this.$message.error("删除配置文件失败，" + message);
				}
			);
		},
	},
};
</script>

<style scoped>
.server-info {
	position: absolute;
	top: 12px;
	left: 12px;
	bottom: 12px;
	right: 12px;
}

.add_button {
	position: absolute;
	bottom: 36px;
	right: 36px;
	height: 48px;
	line-height: 48px;
}
</style>
