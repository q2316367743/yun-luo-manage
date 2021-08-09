<template>
	<el-card class="server-main">
		<div slot="header">
			<span>服务器管理</span>
		</div>
		<div>
			<div>
				<el-button size="mini" type="primary" @click="open_add_server"
					>新增服务器</el-button
				>
				<el-dropdown
					size="small"
					type="primary"
					style="padding-left: 12px"
				>
					<el-button size="mini" type="primary">安装</el-button>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item @click.native="install_server(1)"
							>安装Nginx</el-dropdown-item
						>
						<el-dropdown-item @click.native="install_server(2)"
							>安装Apache</el-dropdown-item
						>
						<el-dropdown-item @click.native="install_server(3)"
							>安装Tomcat</el-dropdown-item
						>
						<el-dropdown-item @click.native="install_server(3)"
							>安装Redis</el-dropdown-item
						>
					</el-dropdown-menu>
				</el-dropdown>
			</div>
			<div>
				<el-table :data="servers" style="width: 100%">
					<el-table-column label="序号" type="index" width="180">
					</el-table-column>
					<el-table-column prop="name" label="名称">
					</el-table-column>
					<el-table-column prop="type" label="服务器类型" width="180">
					</el-table-column>
					<el-table-column prop="version" label="版本" width="180">
					</el-table-column>
					<el-table-column
						prop="create_time"
						label="创建时间"
						width="180"
					>
					</el-table-column>
					<el-table-column label="操作" width="180">
						<template slot-scope="scope">
							<el-button
								type="text"
								size="mini"
								@click="open_info(scope.row.id)"
								>查看</el-button
							>
							<el-button
								type="text"
								size="mini"
								@click="remove(scope.row.id)"
								>删除</el-button
							>
						</template>
					</el-table-column>
				</el-table>
			</div>
		</div>
		<el-dialog
			title="新增服务器"
			:visible.sync="server_dialog"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="50%"
		>
			<el-form label-position="right" label-width="120px">
				<el-form-item label="名称：">
					<el-input v-model="server.name"></el-input>
				</el-form-item>
				<el-form-item label="类型：">
					<el-input v-model="server.type"></el-input>
				</el-form-item>
				<el-form-item label="版本：">
					<el-input v-model="server.version"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="server_dialog = false">取 消</el-button>
				<el-button type="primary" @click="server_dialog_submit"
					>新 增</el-button
				>
			</span>
		</el-dialog>
	</el-card>
</template>

<script src="./server.js"></script>

<style scoped>
.server-main {
	position: absolute;
	top: 12px;
	left: 12px;
	bottom: 12px;
	right: 12px;
}
</style>
