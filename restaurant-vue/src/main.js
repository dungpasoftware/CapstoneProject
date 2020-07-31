// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import cookies from 'vue-cookies'
import {BootstrapVue, BVToastPlugin} from "bootstrap-vue"
import vSelect from "vue-select";
import vMask from 'v-mask'


import VueSweetalert2 from "vue-sweetalert2"

Vue.config.productionTip = false

Vue.prototype.$cookies = cookies
Vue.use(VueSweetalert2)
Vue.use(BootstrapVue)
Vue.use(BVToastPlugin)
Vue.use(vMask)
Vue.component('v-select', vSelect)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
