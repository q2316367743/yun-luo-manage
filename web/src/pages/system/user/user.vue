<template>
	<div class="user-main">
		<el-card style="height: 100%">
			<div slot="header">用户管理</div>
			<el-table :data="users" style="width: 100%">
				<el-table-column type="index" label="序号" width="180">
				</el-table-column>
				<el-table-column prop="username" label="用户登录名" width="180">
				</el-table-column>
				<el-table-column prop="nickname" label="昵称">
				</el-table-column>
				<el-table-column label="启用状态">
					<template slot-scope="scope">
						<el-switch
							v-model="scope.row.is_enable"
							@change="enable(scope.row.id, scope.row.is_enable)"
						>
						</el-switch>
					</template>
				</el-table-column>
				<el-table-column label="所属角色">
					<template slot-scope="scope">
						<el-select
							v-model="scope.row.role_id"
							placeholder="请选择"
							@change="role(scope.row.id, scope.row.role_id)"
						>
							<el-option
								v-for="role in roles"
								:key="role.id"
								:label="role.name"
								:value="role.id"
							>
							</el-option>
						</el-select>
					</template>
				</el-table-column>
				<el-table-column width="180" label="操作">
					<template slot-scope="scope">
						<el-button type="text" @click="reset(scope.row.id)"
							>重置密码</el-button
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
				@click="open_dialog"
			></el-button>
		</div>
		<el-dialog
			:title="user_is_update ? '修改' : '新增' + '用户'"
			:visible.sync="user_dialog"
			v-if="user_dialog"
			destroy-on-close
			append-to-body
			:modal-append-to-body="false"
			:close-on-click-modal="false"
			width="50%"
		>
			<el-form label-position="right" label-width="120px">
				<el-form-item label="用户登录名：">
					<el-input v-model="user.username"></el-input>
				</el-form-item>
				<el-form-item label="旧密码：" v-if="user_is_update">
					<el-input v-model="user.old" type="password"></el-input>
				</el-form-item>
				<el-form-item label="密码：" v-if="!user_is_update">
					<el-input
						v-model="user.password"
						type="password"
					></el-input>
				</el-form-item>
				<el-form-item label="再次输入：" v-if="!user_is_update">
					<el-input v-model="user.agine" type="password"></el-input>
				</el-form-item>
				<el-form-item label="昵称：">
					<el-input v-model="user.nickname"></el-input>
				</el-form-item>
				<el-form-item label="角色：">
					<el-select v-model="user.role_id" placeholder="请选择">
						<el-option
							v-for="role in roles"
							:key="role.id"
							:label="role.name"
							:value="role.id"
						>
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="user_dialog = false">取 消</el-button>
				<el-button type="primary" @click="dialog_submit">{{
					user_is_update ? "修 改" : "新 增"
				}}</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import user_api from "@/apis/user";

export default {
	data: () => ({
		roles: [],
		users: [],
		total: 0,
		page: 0,
		size: 10,
		user_dialog: false,
		user: {
			username: "",
			old: "",
			password: "",
			agine: "",
			nickname: "",
			role_id: "",
		},
	}),
	created() {
		this.get_user();
		user_api.role((res) => {
			if (res.success) {
				this.roles = res.data.items;
			}
		});
	},
	methods: {
		get_user() {
			user_api.page(this.page, this.size, (res) => {
				if (res.success) {
					this.users = res.data.items;
					this.total = res.data.total;
				}
			});
		},
		open_dialog() {
			this.user_dialog = true;
			this.user = {
				username: "",
				old: "",
				password: "",
				agine: "",
				nickname: "",
				role_id: "",
			};
		},
		dialog_submit() {
			// 新增，验证两次密码是否一致
			if (this.user.password !== this.user.agine) {
				this.$message.warning("两次密码不一致");
				return;
			}
			user_api.add(
				{
					username: this.user.username,
					password: this.user.password,
					nickname: this.user.nickname,
					role_id: this.user.role_id,
				},
				(res) => {
					if (res.success) {
						this.user_dialog = false;
						this.$message.success("新增用户成功！");
						this.get_user();
					}
				},
				(message) => {
					this.$message.warning("新增用户失败，" + message);
				}
			);
		},
		enable(id, value) {
			user_api.update(
				id,
				{ is_enable: value },
				(res) => {
					if (res.success) {
						this.$message.success("操作成功！");
						this.get_user();
					}
				},
				(message) => {
					this.$message.warning("操作失败，" + message);
				}
			);
		},
		role(id, value) {
			user_api.update(
				id,
				{ role_id: value },
				(res) => {
					if (res.success) {
						this.$message.success("修改成功！");
						this.get_user();
					}
				},
				(message) => {
					this.$message.warning("修改失败，" + message);
				}
			);
		},
		reset(id) {
			this.$confirm("此操作会将密码重置为123456, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				user_api.reset_password(
					id,
					(res) => {
						if (res.success) {
							this.$message({
								type: "success",
								message: "重置密码成功!",
							});
						}
					},
					(message) => {
						this.$message.warning("重置密码，" + message);
					}
				);
			});
		},
		remove(id) {
			this.$confirm("此操作将删除该用户, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				user_api.remove(
					id,
					(res) => {
						if (res.success) {
							this.$message({
								type: "success",
								message: "删除成功!",
							});
							this.get_user();
						}
					},
					(message) => {
						this.$message.warning("修改失败，" + message);
					}
				);
			});
		},
	},
};
</script>

<style>
.user-main {
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