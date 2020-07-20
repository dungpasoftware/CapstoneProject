import * as types from './mutations_type'
import * as auth from '../networks/Auth'
import * as table from '../networks/Table'
import * as location from '../networks/Location'
import * as order from '../networks/Order'
import * as dish from '../networks/Dish'
import * as category from '../networks/Category'
import * as option from '../networks/Option'
import * as material from '../networks/Material'
import * as inventory from '../networks/Inventory'
import cookies from 'vue-cookies'


//Auth
export const login = ({commit}, loginData) => {
  return auth.loginUser(loginData)
    .then(response => {
      console.log(response)
      commit(types.LOGIN, response.data.roleName);

      cookies.set('user_token', response.data.token);
      cookies.set('user_name', response.data.roleName);
      cookies.set('staff_id', response.data.staffId)
      return response;
    }).catch(error => {
      console.log(error)
      return error
  })
  // commit(types.LOGIN, loginData);
}

export const logout = ({commit}) => {
  commit(types.LOGOUT);
}


//Table
export const setAllTable = ({commit}) => {
  let user_token = cookies.get('user_token');
  table.getAll(user_token)
    .then(({data}) => {
      commit(types.SET_ALL_TABLE, data);
    })
}

export const updateTable = ({commit}, {tableData}) => {
  commit(types.SET_ALL_TABLE, tableData)
}

export const clearAllTable = ({commit}) => {
  commit(types.CLEAR_ALL_TABLE)
}


//Location
export const setAllLocationTable = ({commit}) => {
  let user_token = cookies.get('user_token');
  location.getAll(user_token)
    .then(({data}) => {
      commit(types.SET_ALL_LOCATION_TABLE, data)
    })
}
export const clearAllLocationTable = ({commit}) => {
  commit(types.CLEAR_ALL_LOCATION_TABLE)
}


//Order
export const getOrderById = ({commit}, {orderId}) => {
  let user_token = cookies.get('user_token');
  return order.getById(orderId, user_token)
    .then(response => {
      return response;
    })
}


//Dishes
export const getAllDishes = ({commit}) => {
  let user_token = cookies.get('user_token');
  return dish.getAll(user_token)
}

export const getDishById = ({commit}, id) => {
  let user_token = cookies.get('user_token');
  return dish.getById(user_token, {id})
}

export const addNewDish = ({commit}, dishData) => {
  let user_token = cookies.get('user_token');
  return dish.insetDish(user_token, {dishData})
}

export const editDishById = ({commit}, dishData) => {
  let user_token = cookies.get('user_token');
  return dish.editDish(user_token, {dishData});
}

//Category
export const getAllCategories = ({commit}) => {
  let user_token = cookies.get('user_token');
  return category.getAll(user_token)
}

export const editCategoryById = ({commit}, categoryData) => {
  let user_token = cookies.get('user_token');
  return category.editById(user_token, {categoryData});
}

//Option
export const getAllOptions = ({commit}) => {
  let user_token = cookies.get('user_token');
  return option.getAll(user_token)
}

export const editOptionById = ({commit}, optionData) => {
  let user_token = cookies.get('user_token');
  return option.editById(user_token, {optionData});
}

//Material
export const getAllMaterial = ({commit}) => {
  let user_token = cookies.get('user_token');
  return material.getAll(user_token);
}

//Inventory
export const insertInventory = ({commit}) => {
  let user_token = cookies.get('user_token');
  return inventory.insertInventory(user_token)
}
