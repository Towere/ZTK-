import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
//在经行模块化导入的时候，如果导入这个文件夹下，名字叫做index.js的文件
import router from './router'
import store from './components/vuex/store';
import axios from 'axios';
import VueAxios from 'vue-axios';

Vue.use(VueAxios, axios);
Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
  el: '#app',
  render: h => h(App),
  //在vue项目中，想要把路由用起来，必须把路由实例对象，通过下面的方式进行挂载
  router,
  store
}).$mount('#app')




