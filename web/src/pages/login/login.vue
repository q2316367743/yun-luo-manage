<template>
	<div id="login">
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		<div id="login-main">
			<div id="login-title">云落管理系统</div>
			<el-form label-position="right" label-width="70px">
				<el-form-item label="用户名：">
					<el-input v-model="user.username"></el-input>
				</el-form-item>
				<el-form-item label="密码：">
					<el-input
						v-model="user.password"
						type="password"
						@keydown.enter.native="login"
					></el-input>
				</el-form-item>
			</el-form>
			<div id="login-submit">
				<el-button type="primary" @click="login">登录</el-button>
			</div>
		</div>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
	</div>
</template>

<script>
import { login } from "@/apis/common.js";
export default {
	data: () => ({
		user: {
			username: "",
			password: "",
		},
	}),
	methods: {
		login() {
			login(this.user.username, this.user.password, (res) => {
				if (res.success) {
					sessionStorage.setItem("token", res.data.item.token);
					this.$store.commit("SET_NICKNAME", res.data.item.nickname);
					this.$store.commit("SET_MENU", res.data.item.permissions);
					if (this.$route.query.redict) {
						this.$router.push(this.$route.query.redict);
					} else {
						this.$router.push("/");
					}
				}
			});
		},
	},
};
</script>

<style scoped>
#login {
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	background-color: var(--bg-color);
	display: grid;
	grid-template-columns: 1fr 500px 1fr;
	grid-template-rows: 1fr 300px 1fr;
}

#login-main {
	background-color: var(--main-color);
	border-radius: 10px;
	padding: 24px;
}

#login-title {
	font-size: 30px;
	color: var(--text-color);
	text-align: center;
	height: 70px;
}

#login-submit {
	text-align: center;
}
</style>
