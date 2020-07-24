// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import cookies from 'vue-cookies'
import {BootstrapVue} from "bootstrap-vue"
import vSelect from "vue-select";
import 'vue-select/dist/vue-select.css';


import VueSweetalert2 from "vue-sweetalert2"

Vue.config.productionTip = false

Vue.prototype.$cookies = cookies
Vue.use(VueSweetalert2)
Vue.use(BootstrapVue)
Vue.component('v-select', vSelect)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
