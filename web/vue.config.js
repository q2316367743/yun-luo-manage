module.exports = {
	runtimeCompiler: true,
	devServer: {
		port: 9090,
	},
	publicPath: process.env.NODE_ENV === 'production' ?
        './' :
        '/'
}