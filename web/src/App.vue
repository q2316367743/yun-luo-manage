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
				<el-menu-item index="/">主页</el-menu-item>
				<el-menu-item index="/file">文件管理</el-menu-item>
				<el-submenu index="">
					<template slot="title">Nginx</template>
					<el-menu-item index="/nginx/config">配置文件</el-menu-item>
				</el-submenu>
				<el-menu-item index="/database">数据库管理</el-menu-item>
				<el-menu-item index="/redis">Redis管理</el-menu-item>
				<el-menu-item index="/application">应用管理</el-menu-item>
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
						<template slot="title">管理员</template>
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
import { logout, update } from "@/apis/user";

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
	watch: {
		$route(to) {
			this.index = to.path;
		},
	},
	created() {
		this.index = this.$route.path;
	},
	methods: {
		logout() {
			logout(() => {
				sessionStorage.removeItem("token");
				this.$router.push("/login");
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
	},
};
</script>

<style>
:root {
	--bg-color: #212334;
	--text-color: #ffffff;
	--main-color: #2b2d41;
	--em-color: rgb(31, 33, 46);
	--dis-color: #c0c4cc;
}

#app {
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	overflow: hidden;
}

#left-nav {
	position: absolute;
	top: 0;
	left: 0;
	bottom: 0;
	width: 200px;
	background-color: var(--main-color);
}

#top-nav {
	position: fixed;
	top: 0;
	left: 200px;
	right: 0;
	height: 44px;
	line-height: 44px;
	display: flex;
	justify-content: space-between;
	background-color: var(--bg-color);
	color: var(--text-color);
}

#top-nav > div {
	line-height: 66px;
	padding: 0 40px;
}

#logo {
	height: 46px;
	padding: 10px;
	color: var(--text-color);
}

#main {
	position: fixed;
	top: 44px;
	left: 200px;
	right: 0;
	bottom: 0;
	background-color: var(--bg-color);
	overflow: auto;
}

#title {
	font-size: 1rem;
}

#subtitle {
	color: #f2f2f2;
	font-size: 0.875rem;
}

*::-webkit-scrollbar {
	width: 8px;
	height: 8px;
}

*::-webkit-scrollbar-thumb {
	background-color: var(--main-color);
	background-image: -webkit-linear-gradient(
		45deg,
		rgba(255, 255, 255, 0.1) 25%,
		transparent 25%,
		transparent 50%,
		rgba(255, 255, 255, 0.1) 50%,
		rgba(255, 255, 255, 0.1) 75%,
		transparent 75%,
		transparent
	);
}

*::-webkit-scrollbar-track {
	background-color: transparent;
}

.el-menu {
	border-right: solid 1px var(--main-color) !important;
}
.el-card {
	background-color: var(--main-color) !important;
	color: var(--text-color) !important;
	border: var(--main-color) !important;
}
.el-card__header {
	border-bottom: 1px solid var(--bg-color) !important;
}
.el-menu.el-menu--horizontal {
	border: var(--main-color) !important;
}
.el-submenu__title {
	border-bottom: 2px solid var(--bg-color) !important;
}
.el-form-item__label {
	color: var(--text-color) !important;
}
.el-tabs__item {
	color: var(--text-color) !important;
}
.el-submenu__title {
	height: 44px !important;
	line-height: 44px !important;
}
.el-checkbox-group {
	font-size: 16px !important;
}
.el-checkbox__label {
	color: var(--text-color) !important;
}
.el-message-box {
	background-color: var(--bg-color) !important;
	border: 1px solid var(--bg-color) !important;
}
.el-message-box__content {
	color: var(--text-color) !important;
}
.el-message-box__title {
	color: var(--text-color) !important;
}
.el-input__inner {
	color: var(--text-color) !important;
	background-color: var(--main-color) !important;
	border: 1px solid var(--em-color) !important;
}
.el-select-dropdown__item {
	color: var(--text-color) !important;
	background-color: var(--main-color) !important;
}
.el-select-dropdown {
	color: var(--text-color) !important;
	background-color: var(--main-color) !important;
	border: 0 !important;
}
.el-popper[x-placement^="bottom"] .popper__arrow {
	border-bottom-color: var(--main-color) !important;
}
.el-popper[x-placement^="bottom"] .popper__arrow::after {
	border-bottom-color: var(--main-color) !important;
}

.el-dialog {
	background-color: var(--bg-color) !important;
	border: 1px solid var(--bg-color) !important;
}
.el-dialog__title {
	color: var(--text-color) !important;
}
.el-dialog__body {
	color: var(--text-color) !important;
}
</style>
