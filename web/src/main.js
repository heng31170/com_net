import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

Vue.config.productionTip = false

axios.defaults.headers.common['Content-Type'] = 'application/json';

// 如果有token的需求，可以动态设置
const mytoken = sessionStorage.getItem("token");
if (mytoken) {
  axios.defaults.headers.common['Authorization'] = `${mytoken}`;
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
