module.exports = {
    runtimeCompiler: true,
    devServer: {
        port: 9090,
    },
    publicPath: process.env.NODE_ENV === 'production' ?
        './' : '/',
    outputDir: '../java/src/main/resources/static',
    css: {
        loaderOptions: {
            sass: {
                prependData: `@import "@/assets/scss/style.scss";`
            }
        }
    }
}