<template>
	<div style="padding: 12px">
		<el-card>
			<div slot="header">
				<span>服务器管理</span>
			</div>
			<div>
				<div class="nginx-option">
					<el-dropdown
						size="small"
						type="primary"
						style="padding-left: 12px"
					>
						<el-button size="mini" type="primary">增加</el-button>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item @click.native="open_add_server"
								>新建服务器</el-dropdown-item
							>
							<el-dropdown-item @click.native="install_server(1)"
								>安装Nginx</el-dropdown-item
							>
							<el-dropdown-item @click.native="install_server(2)"
								>安装Apache</el-dropdown-item
							>
							<el-dropdown-item @click.native="install_server(3)"
								>安装Tomcat</el-dropdown-item
							>
						</el-dropdown-menu>
					</el-dropdown>
				</div>
				<div class="nginx-content">
					<el-table :data="servers" style="width: 100%">
						<el-table-column label="序号" type="index" width="180">
						</el-table-column>
						<el-table-column prop="name" label="名称">
						</el-table-column>
						<el-table-column
							prop="type_name"
							label="服务器类型"
							width="180"
						>
						</el-table-column>
						<el-table-column
							prop="version"
							label="版本"
							width="180"
						>
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
									@click="open_setting(scope.row)"
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
		</el-card>
		<el-dialog
			title="服务器详情"
			:visible.sync="info_status"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="50%"
			@close="info_close"
		>
			<default-info
				:id="server_id"
				:is_update="server_is_update"
				v-if="info_status && server_type == 0"
			></default-info
		></el-dialog>
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
					<el-select v-model="server.type" placeholder="请选择">
						<el-option
							v-for="(value, key) in $dict.server_type"
							:key="value"
							:label="key"
							:value="value"
						>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="类型名称：">
					<el-input
						v-model="server.type_name"
						:disabled="server.type !== 0"
					></el-input>
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
	</div>
</template>

<script src="./server.js"></script>

<style scoped>
@import url(./server.css);
</style>