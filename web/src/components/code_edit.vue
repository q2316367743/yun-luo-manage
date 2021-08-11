<template>
	<div>
		<div>
			<span>代码主题：</span>
			<el-select
				v-model="options.theme"
				placeholder="请选择主题"
				style="margin-bottom: 12px"
			>
				<el-option
					v-for="theme in themes"
					:key="theme"
					:label="theme"
					:value="theme"
				>
				</el-option>
			</el-select>
			<span style="padding-left: 12px">语法：</span>
			<el-select
				v-model="options.mode"
				placeholder="请选择语法"
				style="margin-bottom: 12px"
			>
				<el-option
					v-for="(mode, index) in modes"
					:key="index"
					:label="mode.label"
					:value="mode.value"
				>
				</el-option>
			</el-select>
			<span style="padding-left: 12px">编码：</span>
			<el-select
				v-model="charset"
				placeholder="请选择编码"
				style="margin-bottom: 12px"
				@change="open_file"
			>
				<el-option
					v-for="(charset, index) in charsets"
					:key="index"
					:label="charset"
					:value="charset"
				>
				</el-option>
			</el-select>
		</div>
		<codemirror
			v-model="content"
			:options="options"
			style="heigth: 400px"
		></codemirror>
	</div>
</template>

<script>
import file from "@/apis/file.js";

// 引入CodeMirror
import { codemirror } from "vue-codemirror";
// 核心样式
import "codemirror/lib/codemirror.css";
// 引入主题后还需要在 options 中指定主题才会生效
import "codemirror/theme/idea.css";
import "codemirror/theme/rubyblue.css";
import "codemirror/theme/xq-dark.css";
import "codemirror/theme/cobalt.css";
import "codemirror/theme/dracula.css";

// 语法高亮
import "codemirror/mode/javascript/javascript.js";
import "codemirror/mode/css/css.js";
import "codemirror/mode/xml/xml.js";
import "codemirror/mode/markdown/markdown.js";
import "codemirror/mode/python/python.js";
import "codemirror/mode/shell/shell.js";
import "codemirror/mode/sql/sql.js";
import "codemirror/mode/vue/vue.js";
import "codemirror/mode/yaml/yaml.js";

/**
 * 代码编辑器
 */
export default {
	name: "code_edit",
	props: {
		path: String,
		suffix: String,
	},
	components: {
		codemirror,
	},
	data: () => ({
		themes: ["idea", "rubyblue", "xq-dark", "cobalt", "dracula"],
		charsets: ["UTF-8", "ISO-8859-1", "GBK"],
		modes: [
			{
				value: "",
				label: "不使用语法主题",
				suffix: ["txt"],
			},
			{
				value: "css",
				label: "CSS",
				suffix: ["css"],
			},
			{
				value: "javascript",
				label: "Java/Javascript",
				suffix: ["js", "java"],
			},
			{
				value: "html",
				label: "XML/HTML",
				suffix: ["html", "xml"],
			},
			{
				value: "python",
				label: "Python",
				suffix: ["py"],
			},
			{
				value: "x-sh",
				label: "Shell",
				suffix: ["sh"],
			},
			{
				value: "sql",
				label: "SQL",
				suffix: ["sql"],
			},
			{
				value: "vue",
				label: "Vue",
				suffix: ["vue"],
			},
			{
				value: "markdown",
				label: "Markdown",
				suffix: ["md"],
			},
			{
				value: "yaml",
				label: "yaml",
				suffix: ["yml"],
			},
		],
		options: {
			tabSize: 4, // 缩进格式
			theme: "dracula", // 主题，对应主题库 JS 需要提前引入
			lineNumbers: true, // 显示行号
			line: true,
			mode: "",
			styleActiveLine: true, // 高亮选中行
			hintOptions: {
				completeSingle: true, // 当匹配只有一项的时候是否自动补全
			},
		},
		content: "",
		charset: "UTF-8",
	}),
	created() {
		// 判断语法高亮
		for (let mode of this.modes) {
			for (let item of mode.suffix) {
				if (item === this.suffix) {
					this.options.mode = mode.value;
					break;
				}
			}
		}
		this.content = "内容获取中。。。";
		// 获取内容
		this.open_file();
	},
	methods: {
		open_file() {
			file.open(
				this.path,
				this.charset,
				(res) => {
					if (res.success) {
						this.content = res.data.item;
					}
				},
				() => {
					this.content = "内容获取失败";
				}
			);
		},
		get_content() {
			return this.content;
		},
		get_charset() {
			return this.charset;
		},
	},
};
</script>

<style>
</style>