import * as types from './mutations_type'
import * as auth from '../networks/Auth'
import * as table from '../networks/Table'
import * as location from '../networks/Location'
import * as order from '../networks/Order'
import * as dish from '../networks/Dish'
import * as category from '../networks/Category'
import cookies from 'vue-cookies'

const user_token = cookies.get('user_token');

//Auth
export const login = ({commit}, loginData) => {
  return auth.loginUser(loginData)
    .then(response => {
      console.log(response)
      commit(types.LOGIN, response.data.roleName);

      cookies.set('user_token', response.data.token);
      cookies.set('user_name', response.data.roleName);
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


//Cashier
export const getAllTable = ({commit}) => {
  table.getAll(user_token)
    .then(({data}) => {
      commit(types.GET_ALL_TABLE, data);
    })
}

export const editListTable = ({commit}, listTable) => {
  commit(types.GET_ALL_TABLE, listTable);
}

export const getAllLocationTable = ({commit}) => {
  location.getAll(user_token)
    .then(({data}) => {
      commit(types.GET_ALL_LOCATION_TABLE, data)
    })
}

export const getOrderById = ({commit}, {orderId}) => {
  return order.getById(orderId, user_token)
    .then(response => {
      return response;
    })
}


//Dishes
export const getAllDishes = ({commit}) => {
  return dish.getAll(user_token)
}

export const getDishById = ({commit}, id) => {
  return dish.getById(user_token, {id})
}

export const addNewDish = ({commit}, dishData) => {
  return dish.insetDish(user_token, {dishData})
}

export const editDishById = ({commit}, dishData) => {
  return dish.editDish(user_token, {dishData});
}

//Category
export const getAllCategories = ({commit}) => {
  return category.getAll(user_token)
}

export const editCategoryById = ({commit}, categoryData) => {
  return category.editById(user_token, {categoryData});
}
