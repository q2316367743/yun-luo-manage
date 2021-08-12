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
									label="10101"
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
									label="20101"
									@change="
										check_add($event, [
											'20102',
											'20103',
											'20104',
											'20105',
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
									label="20102"
									@change="check_remove($event, '20101')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="20103"
									@change="check_remove($event, '20101')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="20104"
									@change="check_remove($event, '20101')"
									>删除</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="20105"
									@change="check_remove($event, '20101')"
									>详情</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="1">命令管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="20201"
									@change="check_remove($event, '20101')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="20202"
									@change="check_remove($event, '20101')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="20203"
									@change="check_remove($event, '20101')"
									>删除</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="20204"
									@change="check_remove($event, '20101')"
									>执行</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="1">配置管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="20301"
									@change="check_remove($event, '20101')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="20302"
									@change="check_remove($event, '20101')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="20303"
									@change="check_remove($event, '20101')"
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
									label="30101"
									@change="
										check_add($event, [
											'30102',
											'30103',
											'30104',
										])
									"
									>列表</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="30102"
									@change="check_remove($event, '30101')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="30103"
									@change="check_remove($event, '30101')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="30104"
									@change="check_remove($event, '30101')"
									>删除</el-checkbox
								>
							</td>
						</tr>
						<tr>
							<td :rowspan="1">用户管理</td>
							<td>
								<el-checkbox
									v-model="permission_check"
									label="30201"
									@change="
										check_add($event, [
											'30202',
											'30203',
											'30204',
										])
									"
									>列表</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="30202"
									@change="check_remove($event, '30201')"
									>新增</el-checkbox
								><el-checkbox
									v-model="permission_check"
									label="30203"
									@change="check_remove($event, '30201')"
									>修改</el-checkbox
								>
								<el-checkbox
									v-model="permission_check"
									label="30204"
									@change="check_remove($event, '30201')"
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