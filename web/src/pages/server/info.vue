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
					<el-form-item label="进程名：" v-if="is_update">
						<el-input
							v-model="server.application_name"
							style="width: 400px"
						></el-input>
						<el-tooltip
							effect="dark"
							content="应用名称，通过判断系统中是否存在进程来判断状态"
							placement="right"
						>
							<el-button type="text" style="margin-left: 12px"
								>?</el-button
							>
						</el-tooltip>
					</el-form-item>
					<el-form-item label="状态：" v-if="!is_update">
						<div>{{ server.status ? "运行中" : "停止" }}</div>
					</el-form-item>
					<el-form-item>
						<el-button
							@click="is_update = !is_update"
							v-if="permissions.consists('server&u')"
							>{{ is_update ? "取消" : "修改" }}
						</el-button>
						<el-button
							@click="reset"
							v-if="is_update && permissions.consists('server&u')"
							>重置</el-button
						>
						<el-button
							type="primary"
							@click="update"
							v-if="is_update && permissions.consists('server&u')"
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
								v-if="permissions.consists('server&u')"
								>执行</el-button
							>
							<el-button
								type="text"
								@click="open_command_dialog(true, scope.row)"
								v-if="permissions.consists('server&u')"
								>修改</el-button
							>
							<el-button
								type="text"
								@click="command_remove(scope.row.id)"
								v-if="permissions.consists('server&u')"
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
							<el-button
								type="text"
								@click="open_config_edit(scope.row.path)"
								v-if="permissions.consists('server&u')"
								>编辑</el-button
							>
							<el-button
								type="text"
								@click="open_config_dialog(true, scope.row.id)"
								v-if="permissions.consists('server&u')"
								>修改</el-button
							>
							<el-button
								type="text"
								@click="config_remove(scope.row.id)"
								v-if="permissions.consists('server&u')"
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
				v-if="index === 'command' && permissions.consists('server&u')"
				icon="el-icon-plus"
				circle
				@click="open_command_dialog(false)"
				style="font-size: 34px"
			></el-button>
			<el-button
				v-if="index === 'config' && permissions.consists('server&u')"
				icon="el-icon-plus"
				circle
				@click="open_config_dialog(false)"
				style="font-size: 34px"
			></el-button>
		</div>
		<el-dialog
			title="代码编辑器"
			:visible.sync="config_code"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="70%"
			top="10vh"
		>
			<code-edit
				v-if="config_code"
				:path="config_temp_path"
				:suffix="config_temp_suffix"
				ref="code_edit"
			></code-edit>
			<span slot="footer" class="dialog-footer">
				<el-button @click="config_code = false">取 消</el-button>
				<el-button type="primary" @click="write_code_charset"
					>修 改</el-button
				>
			</span>
		</el-dialog>
	</el-card>
</template>

<script>
import server_api from "@/apis/server";
import file_explore from "@/components/file_explore";
import code_edit from "@/components/code_edit";
import { mapGetters } from "vuex";
export default {
	name: "info",
	components: {
		"file-explore": file_explore,
		"code-edit": code_edit,
	},
	data: () => ({
		id: -1,
		is_update: false,
		index: "base",
		server: {
			name: "",
			type: "",
			version: "",
			application_name: "",
			status: false,
		},
		server_default: {
			name: "",
			type: "",
			version: "",
			application_name: "",
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
		config_temp_id: "",
		config_temp_path: "",
		config_temp_suffix: "",
		config_code: false,
	}),
	computed: {
		...mapGetters(["permissions"]),
	},
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
					this.server.application_name =
						this.server_default.application_name;
					this.server.status = this.server_default.status;
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
			this.server.application_name = this.server_default.application_name;
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
		open_config_dialog(config_is_update, id) {
			this.config_dialog = true;
			this.config_is_update = config_is_update;
			this.config_temp_id = id;
		},
		config_dialog_submit() {
			let explore = this.$refs.explore;
			if (!explore.get_is_directory()) {
				if (this.config_is_update) {
					server_api.config_update(
						this.config_temp_id,
						{
							path: explore.get_path(),
						},
						(res) => {
							if (res.success) {
								this.config_dialog = false;
								this.$message.success("修改配置文件成功");
								this.get_server_info();
							}
						},
						(message) => {
							this.$message.error("修改配置文件失败，" + message);
						}
					);
				} else {
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
				}
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
						if (res.data.item === "") {
							this.$message.success("执行成功");
						} else {
							this.$msgbox({
								title: "执行结果",
								message: res.data.item
									.split("\n")
									.join("<br />"),
								closeOnClickModal: false,
								dangerouslyUseHTMLString: true,
							});
						}
					}
				},
				(message) => {
					this.$message.error("执行失败，" + message);
				}
			);
		},
		open_config_edit(path) {
			this.config_temp_path = path;
			let paths = path.split(".");
			this.config_temp_suffix = paths[paths.length - 1];
			this.config_code = true;
		},
		write_code_charset() {
			// 以指定的编码写入
			server_api.config_write(
				this.config_temp_path,
				this.$refs.code_edit.get_charset(),
				this.$refs.code_edit.get_content(),
				(res) => {
					if (res.success) {
						// 关闭对话框
						this.config_code = false;
						this.$message.success("修改成功");
					}
				},
				(message) => {
					this.$message.error("修改失败，" + message);
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
