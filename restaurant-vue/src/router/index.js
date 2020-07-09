import Vue from 'vue'
import Router from 'vue-router'
import Vuex from 'vuex'
import store from '../store'
import cookies from 'vue-cookies'
import HelloWorld from '../components/HelloWorld'
import Login from "../components/auth/Login";
import Backend from "../components/backend/Backend";
import BackendHome from "../components/backend/BackendHome";
import BackendDish from "../components/backend/backenddish/BackendDish";
import BackendDishHome from "../components/backend/backenddish/BackendDishHome";
import BackendDishAddnew from "../components/backend/backenddish/BackendDishAddnew";
import BackendDishEdit from "../components/backend/backenddish/BackendDishEdit";
import BackendCategory from "../components/backend/backendcategory/BackendCategory";
import BackendCategoryHome from "../components/backend/backendcategory/BackendCategoryHome";
import BackendCashier from "../components/backend/backendcashier/BackendCashier";

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    { path: '/', name: 'helloworld', component: HelloWorld },
    { path: '/login', name: 'login', component: Login, beforeEnter: (to, from, next) => {
        if (cookies.get('user_token') !== null) {
          next('/backend');
        } else {
          next();
        }
      } },
    { path: '/backend', component: Backend, children: [
        { path: '', name: 'backend', component: BackendHome },
        { path: 'dish', component: BackendDish, children: [
            { path: '', name: 'backend-dish', component: BackendDishHome },
            { path: 'new', name: 'backend-dish-addnew', component: BackendDishAddnew },
            { path: ':id/edit', name: 'backend-dish-edit', component: BackendDishEdit },
          ] },
        { path: 'category', component: BackendCategory, children: [
            { path: '', name: 'backend-category', component: BackendCategoryHome },
          ] },
        { path: 'cashier', name: 'backend-cashier', component: BackendCashier },
      ], beforeEnter: (to, from, next) => {
        if (cookies.get('user_token') === null) {
          next('/login');
        } else {
          next();
        }
      } },
  ]
})
