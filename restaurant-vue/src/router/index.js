import Vue from 'vue'
import Router from 'vue-router'
import cookies from 'vue-cookies'
import Restaurant from '../components/Restaurant'
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
import BackendOptionAddnew from "../components/backend/backendoption/BackendOptionAddnew";
import BackendOptionEdit from "../components/backend/backendoption/BackendOptionEdit";
import Cashier from "../components/cashier/Cashier";
import BackendInventory from "../components/backend/backendinventory/BackendInventory";
import BackendInventoryHome from "../components/backend/backendinventory/BackendInventoryHome";
import BackendInventoryImport
  from "../components/backend/backendinventory/backendInventoryImport/BackendInventoryImport";
import BackendInventoryImportHome
  from "../components/backend/backendinventory/backendInventoryImport/BackendInventoryImportHome";
import BackendMaterialReport from "../components/backend/backendmaterial/backendmaterialreport/BackendMaterialReport"
import BackendMaterialReportHome from "../components/backend/backendmaterial/backendmaterialreport/BackendMaterialReportHome"
import BackendStaff from "../components/backend/backendstaff/BackendStaff";
import BackendStaffHome from "../components/backend/backendstaff/BackendStaffHome";

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    { path: '/', component: Restaurant, beforeEnter: (to, from, next) => {
        if ( cookies.get('user_token') === null ) {
          next( {name: 'login'} );
        } else {
          if (cookies.get('role_name') === 'ROLE_MANAGER') {
            next( {name: 'backend'} );
          } else {
            next( {name: 'cashier'} );
          }
        }
      }},
    { path: '/login', name: 'login', meta: { title: 'Đăng nhập' }, component: Login, beforeEnter: (to, from, next) => {
      if (cookies.get('user_token') !== null) {
        next({ name: 'backend' });
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
        { path: 'material/report', component: BackendMaterialReport, children: [
          { path: '', name: 'backend-material-report', component: BackendMaterialReportHome },
          ]
        }], beforeEnter: (to, from, next) => {
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
          next( {name: 'backend'} );
        } else {
          next();
        }
      }
    }},
  ]
})
