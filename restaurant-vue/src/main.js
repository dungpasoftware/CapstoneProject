// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import cookies from 'vue-cookies'
import {BootstrapVue} from "bootstrap-vue";

import VueSweetalert2 from "vue-sweetalert2"

Vue.config.productionTip = false

Vue.prototype.$cookies = cookies
Vue.prototype.$socket = null;
Vue.prototype.$stompClient = null;
Vue.use(VueSweetalert2)
Vue.use(BootstrapVue)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
