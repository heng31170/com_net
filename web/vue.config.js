const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:5678', // 后端接口地址
        changeOrigin: true,
        secure: false,  // 如果是 https 的服务端，设置为 true
        pathRewrite: {
          '^/api': ''  // 可以改写路径，去掉 `/api` 前缀
        }
      }
    }
  }
})
