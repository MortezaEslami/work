module.exports = {
    devServer: {
        port: 8766
    },
    runtimeCompiler: true,
    pluginOptions: {
        i18n: {
            locale: 'en',
            fallbackLocale: 'de',
            localeDir: 'locales',
            enableInSFC: false
        }
    }
}
