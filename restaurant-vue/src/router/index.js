import Vue from 'vue'
import Router from 'vue-router'
import cookies from 'vue-cookies'
import Restaurant from '../components/Restaurant'
import Login from "../components/auth/login";
import Backend from "../components/backend";
import BackendHome from "../components/backend/List";
import BackendDish from "../components/backend/dish";
import BackendDishHome from "../components/backend/dish/List";
import BackendDishAddnew from "../components/backend/dish/Add";
import BackendDishEdit from "../components/backend/dish/Edit";
import BackendCategory from "../components/backend/category";
import BackendCategoryHome from "../components/backend/category/List";
import BackendOption from "../components/backend/option";
import BackendOptionHome from "../components/backend/option/List";
import BackendOptionAddnew from "../components/backend/option/Add";
import BackendOptionEdit from "../components/backend/option/Edit";
import Cashier from "../components/cashier";
import BackendInventory from "../components/backend/material";
import BackendInventoryHome from "../components/backend/material/List";
import BackendInventoryImport
  from "../components/backend/material/import";
import BackendInventoryImportHome
  from "../components/backend/material/import/List";
import BackendStaff from "../components/backend/staff";
import BackendStaffHome from "../components/backend/staff/List";
import BackendReportTopdish from "../components/backend/report/top-dish/BackendReportTopdish";
import BackendReportTopdishHome from "../components/backend/report/top-dish/BackendReportTopdishHome";
import BackendReportOrder from "../components/backend/report/order";
import BackendReportOrderHome from "../components/backend/report/order/List";
import BackendReportInventory from "../components/backend/inventory/report";
import BackendReportInventoryHome from "../components/backend/inventory/report/List";
import ErrorPage from "../components/404";

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    { path: '/', component: Restaurant, beforeEnter: (to, from, next) => {
        if ( cookies.get('user_token') === null ) {
          next( {name: 'login'} );
        } else {
          if (cookies.get('role_name') === 'ROLE_MANAGER') {
            next( {name: 'backend-dish'} );
          } else {
            next( {name: 'cashier'} );
          }
        }
      }},
    { path: '/login', name: 'login', meta: { title: 'Đăng nhập' }, component: Login, beforeEnter: (to, from, next) => {
      if (cookies.get('user_token') !== null) {
        next({ name: 'backend-dish' });
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
            { path: 'new', name: 'backend-option-addnew', component: BackendOptionAddnew },
            { path: ':id/edit', name: 'backend-option-edit', component: BackendOptionEdit },
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
        // { path: 'report/topdish', component: BackendReportTopdish, children: [
        //     { path: '', name: 'report-topdish', component: BackendReportTopdishHome },
        //   ]},
        // { path: 'report/order', component: BackendReportOrder, children: [
        //     { path: '', name: 'report-order', component: BackendReportOrderHome },
        //   ]},
        { path: 'report/inventory', component: BackendReportInventory, children: [
            { path: '', name: 'report-inventory', component: BackendReportInventoryHome },
          ]},
        ], beforeEnter: (to, from, next) => {
      if ( cookies.get('user_token') === null ) {
        next( {name: 'login'} );
      } else {
        if (cookies.get('role_name') === 'ROLE_CASHIER') {
          next( {name: 'cashier'} );
        } else {
          next();
        }
      }
    } },
    { path: '/cashier', name: 'cashier', component: Cashier, beforeEnter:  (to, from, next) => {
      if ( cookies.get('user_token') === null ) {
        next( {name: 'login'} );
      } else {
        if (cookies.get('role_name') === 'ROLE_MANAGER') {
          next( {name: 'backend-dish'} );
        } else {
          next();
        }
      }
    }},
    { path: '/error', name: 'error', component: ErrorPage },
    { path: '*', redirect: { name: 'error' } }
  ]
})
