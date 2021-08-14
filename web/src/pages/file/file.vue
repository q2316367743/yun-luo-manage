<template>
	<div class="file-main">
		<div class="el-card" style="height: 100%">
			<div class="file-path">
				<input
					class="file-path-input"
					v-model="to_path_temp"
					v-if="to_path_status"
					ref="to_path_input"
					@blur="cancel_path_input"
					@keydown.enter="path_input_to"
				/>
				<div class="file-path-items" v-else @click="open_path_input">
					<span v-for="(item, index) in show_path" :key="index">
						<span v-if="index != 0" style="padding: 0 3px">/</span>
						<el-button type="text" @click.stop="toP(item.path)">{{
							item.name
						}}</el-button>
					</span>
				</div>
			</div>
			<div class="file-option">
				<el-upload
					action=""
					:show-file-list="false"
					:http-request="on_upload"
				>
					<el-button size="mini" type="primary">上传</el-button>
				</el-upload>
				<el-button
					size="mini"
					type="primary"
					style="margin-left: 12px"
					@click="open_remote_download"
					>远程下载</el-button
				>
				<el-dropdown
					size="small"
					type="primary"
					style="padding-left: 12px"
				>
					<el-button size="mini" class="file-button">新建</el-button>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item @click.native="mkdir"
							>新建文件夹</el-dropdown-item
						>
						<el-dropdown-item @click.native="touch"
							>新建文件</el-dropdown-item
						>
					</el-dropdown-menu>
				</el-dropdown>
				<el-button-group
					style="padding-left: 12px"
					v-if="paths.length > 0"
				>
					<el-button
						size="mini"
						class="file-button"
						@click="multi_download"
						>下载</el-button
					>
					<el-button size="mini" class="file-button" @click="rm_all"
						>删除</el-button
					>
					<el-button
						size="mini"
						class="file-button"
						@click="mv"
						:disabled="mv_paths.length > 0 || cp_paths.length > 0"
						>移动到</el-button
					>
					<el-button
						size="mini"
						class="file-button"
						@click="cp"
						:disabled="mv_paths.length > 0 || cp_paths.length > 0"
						>复制到</el-button
					>
				</el-button-group>
				<el-button
					style="margin-left: 12px"
					size="mini"
					@click="paste"
					class="file-button"
					v-if="mv_paths.length > 0 || cp_paths.length > 0"
					>黏贴到此处</el-button
				>
				<el-button
					style="margin-left: 12px"
					size="mini"
					@click="canel"
					class="file-button"
					v-if="mv_paths.length > 0 || cp_paths.length > 0"
					>取消{{ mv_paths.length > 0 ? "移动" : "复制" }}</el-button
				>
			</div>
			<div class="file-show" @contextmenu.prevent="option_menu_open">
				<div class="file-head">
					<div class="file-name">
						<el-checkbox
							:indeterminate="is_check_all"
							v-model="check_all"
							@change="handleCheckAllChange"
							:disabled="file_list.length === 0"
						></el-checkbox>
						<span>文件名</span>
					</div>
					<div class="file-item-center">文件大小</div>
					<div>修改时间</div>
				</div>
				<div class="file-content">
					<div
						class="file-content-item"
						v-for="(file, index) in file_list"
						:key="index"
						@dblclick="open(file)"
						@contextmenu.prevent.stop="
							open_menu(index, file, $event)
						"
						:class="{
							'file-content-hover': index === menu_index,
						}"
					>
						<div class="file-name">
							<el-checkbox v-model="paths" :label="file.path">
								<i
									class="el-icon-folder-opened"
									v-if="file.type === 'FOLDER'"
								></i>
								<i
									class="el-icon-document"
									v-else-if="file.type === 'FILE'"
								></i>
							</el-checkbox>
							<span>{{ file.name }}</span>
						</div>
						<div class="file-item-center">{{ file.size }}</div>
						<div>{{ file.update_time }}</div>
					</div>
				</div>
			</div>
		</div>
		<div
			id="file-menu-bg"
			@click="close_menu"
			@contextmenu.prevent="close_menu"
		></div>
		<div id="file-menu" class="my-menu">
			<div @click="menu_open">打开</div>
			<div
				v-if="this.menu_file && this.menu_file.type === 'FILE'"
				@click="
					() => {
						menu_open_by_code();
						close_menu();
					}
				"
			>
				文本格式打开
			</div>
			<div
				v-if="this.menu_file && this.menu_file.type === 'FILE'"
				@click="
					() => {
						open_by_image(this.menu_file.name);
						close_menu();
					}
				"
			>
				图片格式打开
			</div>
			<div
				v-if="this.menu_file && this.menu_file.type === 'FILE'"
				@click="
					() => {
						open_by_video(this.menu_file.name);
						close_menu();
					}
				"
			>
				视频格式打开
			</div>
			<div
				@click="
					() => {
						download(this.menu_file);
						close_menu();
					}
				"
			>
				下载
			</div>
			<div
				@click="
					() => {
						refresh();
						close_menu();
					}
				"
			>
				刷新
			</div>
			<div
				@click="
					() => {
						rm(menu_file.path, true);
						close_menu();
					}
				"
			>
				删除
			</div>
			<div
				@click="
					() => {
						rename(menu_file.name);
						close_menu();
					}
				"
			>
				重命名
			</div>
			<div
				@click="
					() => {
						stat();
						close_menu();
					}
				"
			>
				属性
			</div>
		</div>
		<div id="option-menu" class="my-menu">
			<div
				@click="
					() => {
						touch();
						close_menu();
					}
				"
			>
				新建文件
			</div>
			<div
				@click="
					() => {
						mkdir();
						close_menu();
					}
				"
			>
				新建文件夹
			</div>
			<div
				@click="
					() => {
						refresh();
						close_menu();
					}
				"
			>
				刷新
			</div>
			<div
				v-if="mv_paths.length > 0 || cp_paths.length > 0"
				@click="
					() => {
						paste();
						close_menu();
					}
				"
			>
				粘贴
			</div>
			<div
				@click="
					() => {
						stat();
						close_menu();
					}
				"
			>
				属性
			</div>
		</div>
		<el-dialog
			title="代码编辑器"
			:visible.sync="code"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="70%"
			top="10vh"
		>
			<code-edit
				v-if="code"
				:path="code_path"
				:suffix="suffix"
				ref="code_edit"
			></code-edit>
			<span slot="footer" class="dialog-footer">
				<el-button @click="code = false">取 消</el-button>
				<el-button type="primary" @click="write_code_charset"
					>修 改</el-button
				>
			</span>
		</el-dialog>
		<el-dialog
			title="远程下载"
			:visible.sync="remote_download_status"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="50%"
		>
			<el-form
				v-model="remote_download_value"
				label-position="right"
				label-width="80px"
			>
				<el-form-item label="远程地址">
					<el-input v-model="remote_download_value.url"></el-input>
				</el-form-item>
				<el-form-item label="文件名称">
					<el-input
						v-model="remote_download_value.name"
						placeholder="如果为空则使用默认文件名"
					></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="remote_download_status = false"
					>取 消</el-button
				>
				<el-button type="primary" @click="remote_download"
					>下 载</el-button
				>
			</span>
		</el-dialog>
		<el-dialog
			title="属性"
			:visible.sync="info_status"
			:modal-append-to-body="false"
			destroy-on-close
			:close-on-click-modal="false"
			width="30%"
			@close="
				() => {
					this.info_index = 'base';
					this.info_status = false;
				}
			"
		>
			<el-tabs v-model="info_index" stretch>
				<el-tab-pane label="基本信息" name="base"> </el-tab-pane>
				<el-tab-pane label="权限" name="access"></el-tab-pane>
				<div v-if="info_index === 'base'" style="margin-top: 12px">
					<el-form label-width="100px">
						<el-form-item label="文件路径：">
							{{ info.name }}
						</el-form-item>
						<el-form-item label="创建时间：">
							{{ info.birth }}
						</el-form-item>
						<el-form-item label="最近访问：">
							{{ info.access_time }}
						</el-form-item>
						<el-form-item label="最近更改：">
							{{ info.modify_time }}
						</el-form-item>
						<el-form-item label="最近改动：">
							{{ info.change_time }}
						</el-form-item>
					</el-form>
				</div>
				<div v-if="info_index === 'access'" style="margin-top: 12px">
					<el-form label-width="100px">
						<el-form-item label="所属组：">
							{{ info.group }}
						</el-form-item>
						<el-form-item label="所属用户：">
							{{ info.user }}
						</el-form-item>
						<el-form-item label="组权限：">
							<el-checkbox v-model="info.access.group.write"
								>写入</el-checkbox
							>
							<el-checkbox v-model="info.access.group.read"
								>读取</el-checkbox
							>
							<el-checkbox v-model="info.access.group.execute"
								>执行</el-checkbox
							>
						</el-form-item>
						<el-form-item label="用户权限：">
							<el-checkbox v-model="info.access.owner.write"
								>写入</el-checkbox
							>
							<el-checkbox v-model="info.access.owner.read"
								>读取</el-checkbox
							>
							<el-checkbox v-model="info.access.owner.execute"
								>执行</el-checkbox
							>
						</el-form-item>
						<el-form-item label="其他权限：">
							<el-checkbox v-model="info.access.other.write"
								>写入</el-checkbox
							>
							<el-checkbox v-model="info.access.other.read"
								>读取</el-checkbox
							>
							<el-checkbox v-model="info.access.other.execute"
								>执行</el-checkbox
							>
						</el-form-item>
					</el-form>
				</div>
			</el-tabs>
		</el-dialog>
	</div>
</template>

<script src="./file.js"></script>

<style scoped>
@import url(./file.css);
</style>