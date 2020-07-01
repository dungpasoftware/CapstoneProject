import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'
import Login from "../components/auth/Login";
import Backend from "../components/backend/Backend";
import BackendHome from "../components/backend/BackendHome";
import BackendFood from "../components/backend/BackendFood";
import BackendCashier from "../components/backend/BackendCashier";

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', name: 'helloworld', component: HelloWorld },
    { path: '/login', name: 'login', component: Login },
    { path: '/backend', component: Backend, children: [
        { path: '', name: 'backend', component: BackendHome },
        { path: 'food', name: 'food', component: BackendFood },
        { path: 'cashier', name: 'cashier', component: BackendCashier },
      ] },
  ]
})
