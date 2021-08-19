<template>
	<div id="app">
		<div id="left-nav">
			<div id="logo">
				<div id="title">云落管理</div>
				<div id="subtitle">Linux</div>
			</div>
			<el-menu
				:default-active="index"
				background-color="#2B2D41"
				text-color="#ffffff"
				router
			>
				<el-menu-item index="/">
					<span slot="title">首页</span>
				</el-menu-item>
				<el-menu-item
					index="/file"
					v-if="permissions.startWith('file')"
				>
					<span slot="title">文件管理</span>
				</el-menu-item>
				<el-menu-item
					index="/server"
					v-if="permissions.consists('server&l')"
				>
					<span slot="title">服务器管理</span>
				</el-menu-item>
				<el-submenu
					index="/system"
					v-if="permissions.startWith('system')"
				>
					<template slot="title">
						<span>系统管理</span>
					</template>
					<el-menu-item
						index="/system/role"
						v-if="permissions.consists('system-role&l')"
						>角色管理</el-menu-item
					><el-menu-item
						index="/system/user"
						v-if="permissions.consists('system-user&l')"
						>用户管理</el-menu-item
					>
				</el-submenu>
			</el-menu>
		</div>
		<div id="top-nav">
			<div></div>
			<div>
				<el-menu
					mode="horizontal"
					background-color="#212334"
					text-color="#ffffff"
					active-text-color="#ffffff"
				>
					<el-submenu index="">
						<template slot="title">{{ nickname }}</template>
						<el-menu-item index="" @click="update_status = true"
							>修改账户</el-menu-item
						>
						<el-menu-item index="" @click="logout"
							>退出</el-menu-item
						>
					</el-submenu>
				</el-menu>
			</div>
		</div>
		<!-- 根据应用组件来调整你的内容 -->
		<div id="main">
			<router-view></router-view>
		</div>
		<el-dialog
			title="修改账户信息"
			:visible.sync="update_status"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="50%"
		>
			<el-form v-model="user" label-position="right" label-width="100px">
				<el-form-item label="用户名：">
					<el-input
						v-model="user.username"
						placeholder="如不修改可不输入"
					></el-input>
				</el-form-item>
				<el-form-item label="旧密码：">
					<el-input v-model="user.old" type="password"></el-input>
				</el-form-item>
				<el-form-item label="新密码：">
					<el-input
						v-model="user.password"
						type="password"
					></el-input>
				</el-form-item>
				<el-form-item label="再次输入：">
					<el-input v-model="user.once" type="password"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="update_status = false">取 消</el-button>
				<el-button type="primary" @click="update_user">修 改</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
import { logout, update, info } from "@/apis/common";

export default {
	name: "App",

	data: () => ({
		index: "/",
		update_status: false,
		user: {
			username: "",
			old: "",
			password: "",
			once: "",
		},
	}),
	computed: {
		...mapGetters(["permissions", "nickname"]),
	},
	created() {
		let index = this.$route.path;
		if (index.startsWith("/server")) {
			index = "/server";
		}
		this.index = index;
		info((res) => {
			if (res.success) {
				this.$store.commit("SET_NICKNAME", res.data.item.nickname);
				this.$store.commit("SET_MENU", res.data.item.permissions);
			}
		});
	},
	methods: {
		logout() {
			logout(() => {
				sessionStorage.removeItem("token");
				this.$router.push("/login");
				location.reload();
			});
		},
		update_user() {
			if (
				this.user.old.length === 0 ||
				this.user.password.length === 0 ||
				this.user.once.length === 0
			) {
				this.$message.error("输入框不能为空！");
				return;
			}
			if (this.user.password !== this.user.once) {
				this.$message.error("两次密码输入不一致！");
				return;
			}
			update(
				this.user.username,
				this.user.old,
				this.user.password,
				(res) => {
					if (res.success) {
						this.update_status = false;
						this.user = {
							username: "",
							old: "",
							password: "",
							once: "",
						};
						this.$message.success("修改成功，请重新登录！");
						sessionStorage.removeItem("token");
						this.$router.push("/login");
					}
				},
				(message) => {
					this.$message.error(message);
				}
			);
		},
		consist(list, keyword) {
			for (let item of list) {
				if (item.indexOf(keyword) != -1) {
					return true;
				}
			}
			return false;
		},
	},
};
</script>

<style>
</style>
