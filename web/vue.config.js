module.exports = {
    transpileDependencies: [
        'vuetify'
    ],
    runtimeCompiler: true,
    devServer: {
        port: 9090,
    },
    publicPath: process.env.NODE_ENV === 'production' ?
        './' :
        '/',
    css: {
        loaderOptions: {
            sass: {
                prependData: `@import "@/assets/scss/style.scss";`
            }
        }
    }
}
