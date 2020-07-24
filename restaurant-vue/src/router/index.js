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
import BackendOption from "../components/backend/backendoption/BackendOption";
import BackendOptionHome from "../components/backend/backendoption/BackendOptionHome";
import BackendCashier from "../components/backend/backendcashier/BackendCashier";
import TestSocket from "../components/template/TestSocket";
import BackendMaterial from "../components/backend/backendmaterial/BackendMaterial";
import BackendMaterialHome from "../components/backend/backendmaterial/BackendMaterialHome";
import BackendInventory from "../components/backend/backendinventory/BackendInventory";
import BackendInventoryHome from "../components/backend/backendinventory/BackendInventoryHome";
import BackendInventoryImport
  from "../components/backend/backendinventory/backendInventoryImport/BackendInventoryImport";
import BackendInventoryImportHome
  from "../components/backend/backendinventory/backendInventoryImport/BackendInventoryImportHome";
import BackendStaff from "../components/backend/backendstaff/BackendStaff";
import BackendStaffHome from "../components/backend/backendstaff/BackendStaffHome";

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
          ]},
        { path: 'category', component: BackendCategory, children: [
            { path: '', name: 'backend-category', component: BackendCategoryHome },
          ]},
        { path: 'option', component: BackendOption, children: [
            { path: '', name: 'backend-option', component: BackendOptionHome },
          ]},
        { path: 'material', component: BackendMaterial, children: [
            { path: '', name: 'backend-material', component: BackendMaterialHome },
          ]},
        { path: 'inventory', component: BackendInventory, children: [
            { path: '', name: 'backend-inventory', component: BackendInventoryHome },
          ]},
        { path: 'inventory/import', component: BackendInventoryImport, children: [
            { path: '', name: 'backend-inventory-import', component: BackendInventoryImportHome }
          ]},
        { path: 'staff', component: BackendStaff, children: [
            { path: '', name: 'backend-staff', component: BackendStaffHome },
          ]},
        { path: 'cashier', name: 'backend-cashier', component: BackendCashier },
      ], beforeEnter: (to, from, next) => {
        if (cookies.get('user_token') === null) {
          next( {name: 'login'} );
        } else {
          next();
        }
      } },
  ]
})
