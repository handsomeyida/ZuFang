import Vue from 'vue'
import 'normalize.css/normalize.css'// A modern alternative to CSS resets
import ElementUI, {Message} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import App from './App'
import router from './router'
import store from './store'
import '@/icons' // icon
import '@/permission' // 权限
import {default as api} from './utils/api'
import {hasPermission} from "./utils/hasPermission";
import axios from 'axios'
import echarts from 'echarts' //引入echarts
import areaName from './utils/area-ch'

Vue.prototype.areaName = areaName//引入全球中文地名
Vue.prototype.$echarts = echarts //引入组件
Vue.prototype.$reqs = axios
Vue.use(ElementUI, {locale})
Vue.prototype.api = api
Vue.component(Message)
Vue.prototype.$message = Message

//全局的常量
Vue.prototype.hasPerm = hasPermission
//生产环境时自动设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = (process.env.NODE_ENV != 'production')
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
