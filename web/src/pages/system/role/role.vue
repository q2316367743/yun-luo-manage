<template>
	<div class="role-main">
		<el-card style="height: 100%">
			<div slot="header">角色管理</div>
			<el-table :data="roles" style="width: 100%">
				<el-table-column type="index" label="序号" width="180">
				</el-table-column>
				<el-table-column prop="name" label="名称" width="180">
				</el-table-column>
				<el-table-column prop="value" label="唯一标识">
				</el-table-column>
				<el-table-column prop="create_time" label="创建时间">
				</el-table-column>
				<el-table-column prop="update_time" label="更新时间">
				</el-table-column>
				<el-table-column width="180" label="操作">
					<template slot-scope="scope">
						<el-button
							type="text"
							@click="open_dialog(true, scope.row.id)"
							>修改</el-button
						>
						<el-button type="text" @click="remove(scope.row.id)"
							>删除</el-button
						>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<div class="add_button">
			<el-button
				icon="el-icon-plus"
				circle
				style="font-size: 34px"
				@click="open_dialog(false)"
			></el-button>
		</div>
		<el-dialog
			:title="role_is_update ? '修改' : '新增' + '角色'"
			:visible.sync="role_dialog"
			v-if="role_dialog"
			destroy-on-close
			append-to-body
			:modal-append-to-body="false"
			:close-on-click-modal="false"
			width="50%"
			top="5vh"
		>
			<el-form label-position="right" label-width="120px">
				<el-form-item label="名称：">
					<el-input v-model="role.name"></el-input>
				</el-form-item>
				<el-form-item label="唯一标识">
					<el-input v-model="role.value"></el-input>
				</el-form-item>
				<el-form-item label="权限">
					<table class="el-table">
						<thead>
							<tr>
								<th id="category" class="is-leaf">权限</th>
								<th id="permission" class="is-leaf">权限</th>
								<th id="operations" class="is-leaf">权限</th>
							</tr>
						</thead>
						<tr>
							<td :rowspan="1">文件管理</td>
							<td :rowspan="1">文件管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="1"
									>文件管理</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="3">服务器管理</td>
							<td :rowspan="1">服务器管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="2"
									@change="
										check_add($event, [
											'3',
											'4',
											'5',
											'6',
											'7',
											'8',
											'9',
											'10',
											'11',
											'12',
											'13',
										])
									"
									>列表</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="3"
									@change="check_remove($event, '2')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="4"
									@change="check_remove($event, '2')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="5"
									@change="check_remove($event, '2')"
									>删除</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="6"
									@change="check_remove($event, '2')"
									>详情</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="1">命令管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="7"
									@change="check_remove($event, '2')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="8"
									@change="check_remove($event, '2')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="9"
									@change="check_remove($event, '2')"
									>删除</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="10"
									@change="check_remove($event, '2')"
									>执行</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="1">配置管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="11"
									@change="check_remove($event, '2')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="12"
									@change="check_remove($event, '2')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="13"
									@change="check_remove($event, '2')"
									>删除</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="2">系统管理</td>
							<td :rowspan="1">角色管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="14"
									@change="
										check_add($event, ['15', '16', '17'])
									"
									>列表</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="15"
									@change="check_remove($event, '14')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="16"
									@change="check_remove($event, '14')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="17"
									@change="check_remove($event, '14')"
									>删除</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="1">用户管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="18"
									@change="
										check_add($event, ['19', '20', '21'])
									"
									>列表</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="19"
									@change="check_remove($event, '18')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="20"
									@change="check_remove($event, '18')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="21"
									@change="check_remove($event, '18')"
									>删除</el-checkbox
								>
							</td>
						</tr>
					</table>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="role_dialog = false">取 消</el-button>
				<el-button type="primary" @click="dialog_submit">{{
					role_is_update ? "修 改" : "新 增"
				}}</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import role_api from "@/apis/role";

function consist(list, keyword) {
	for (let item of list) {
		if (item.indexOf(keyword) != -1) {
			return true;
		}
	}
	return false;
}

export default {
	data: () => ({
		roles: [],
		role: {
			name: "",
			value: "",
		},
		permission_check: [],
		role_dialog: false,
		role_is_update: false,
		role_temp_id: "",
	}),
	created() {
		this.get_role_list();
	},
	methods: {
		get_role_list() {
			role_api.list((res) => {
				if (res.success) {
					this.roles = res.data.items;
				}
			});
		},
		open_dialog(role_is_update, id) {
			if (role_is_update) {
				this.role_temp_id = id;
				// 更新
				role_api.info(id, (res) => {
					if (res.success) {
						this.role = res.data.item;
						this.permission_check = this.role.permissions;
					}
				});
			} else {
				// 新增
				this.role = {
					name: "",
					value: "",
				};
				this.permission_check = [];
			}
			this.role_is_update = role_is_update;
			this.role_dialog = true;
		},
		dialog_submit() {
			this.role_dialog = false;
			if (this.role_is_update) {
				// 更新
				role_api.update(
					this.role_temp_id,
					{
						name: this.role.name,
						value: this.role.value,
						permission_ids: this.permission_check,
					},
					(res) => {
						if (res.success) {
							this.$message.success("修改角色成功");
							this.get_role_list();
						}
					},
					(message) => {
						this.$message.error("修改角色失败，" + message);
					}
				);
			} else {
				// 新增
				role_api.add(
					{
						name: this.role.name,
						value: this.role.value,
						permission_ids: this.permission_check,
					},
					(res) => {
						if (res.success) {
							this.$message.success("新增角色成功");
							this.get_role_list();
						}
					},
					(message) => {
						this.$message.error("新增角色失败，" + message);
					}
				);
			}
		},
		remove(id) {
			this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				role_api.remove(
					id,
					(res) => {
						if (res.success) {
							this.$message({
								type: "success",
								message: "删除成功!",
							});
							this.get_role_list();
						}
					},
					(message) => {
						this.$message({
							type: "error",
							message: "删除失败，" + message,
						});
					}
				);
			});
		},
		check_add(value, ids) {
			if (!value) {
				for (let id of ids) {
					this.permission_check = this.permission_check.filter(
						(item) => {
							return item !== id;
						}
					);
				}
			}
		},
		check_remove(value, id) {
			if (value) {
				if (!consist(this.permission_check, id)) {
					this.permission_check.push(id);
				}
			}
		},
	},
};
</script>

<style scoped>
.role-main {
	position: absolute;
	top: 12px;
	left: 12px;
	bottom: 12px;
	right: 12px;
}
.add_button {
	position: absolute;
	bottom: 48px;
	right: 36px;
	height: 48px;
	line-height: 48px;
}
</style>