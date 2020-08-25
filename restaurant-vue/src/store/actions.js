import * as types from './mutations_type'
import * as auth from '../networks/Auth'
import * as table from '../networks/Table'
import * as location from '../networks/Location'
import * as order from '../networks/Order'
import * as dish from '../networks/Dish'
import * as category from '../networks/Category'
import * as option from '../networks/Option'
import * as material from '../networks/Material'
import * as groupMaterial from '../networks/GroupMaterial'
import * as supplier from '../networks/Supplier'
import * as warehouse from '../networks/Warehouse'
import * as inventory from '../networks/Inventory'
import * as inventoryMaterial from '../networks/InventoryMaterial'
import * as imgurAPI from '../networks/ImgurAPI'
import cookies from 'vue-cookies'


//Auth
export const login = ({commit}, loginData) => {
  let user_token = cookies.get('user_token');
  return auth.loginUser(user_token, loginData);
}

export const addUserName = ({commit}, userName) => {
  commit(types.LOGIN, userName);
}

export const addUserData = ({commit}, userData) => {
  commit(types.SET_USERDATA_FROM_COOKIES, userData);
}

export const logout = ({commit}) => {
  cookies.remove('user_token');
  cookies.remove('role_name');
  cookies.remove('staff_code');
  cookies.remove('staff_id');
  commit(types.LOGOUT);
}

export const checkLogin = ({commit}) => {
  let user_token = cookies.get('user_token');
  return auth.preLogin(user_token);
}

export const closeCheckLogin = ({commit}) => {
  commit(types.CLOSE_LOADING_CHECK_LOGNIN);
}

export const openLoader = ({commit}) => {
  commit(types.OPEN_LOADING);
}

export const closeLoader = ({commit}) => {
  commit(types.CLOSE_LOADING);
}


//Table
export const setAllTable = ({commit}) => {
  let user_token = cookies.get('user_token');
  return table.getAll(user_token)
}


//Location
export const setAllLocationTable = ({commit}) => {
  let user_token = cookies.get('user_token');
  return location.getAll(user_token)
}


//Order
export const getOrderById = ({commit}, {orderId}) => {
  let user_token = cookies.get('user_token');
  return order.getById(orderId, user_token)
    .then(response => {
      return response;
    })
}

export const acceptOrderPayment = ({commit}, orderData) => {
  let user_token = cookies.get('user_token');
  return order.acceptPayment(user_token, orderData);
}

export const cancelOrderPayment = ({commit}, orderData) => {
  let user_token = cookies.get('user_token');
  return order.cancelAcceptPayment(user_token, orderData);
}

export const paymentOrder = ({commit}, paymentData) => {
  let user_token = cookies.get('user_token');
  return order.payment(user_token, paymentData);
}

export const cancelOrder = ({commit}, orderData) => {
  let user_token = cookies.get('user_token');
  return order.cancel(user_token, orderData);
}

//Dishes
export const getAllDishes = ({commit}) => {
  let user_token = cookies.get('user_token');
  return dish.getAll(user_token)
}

export const searchALlDishes = ({commit}, {id, name, page}) => {
  let user_token = cookies.get('user_token');
  return dish.searchAll(user_token, {id, name, page})
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

export const deleteDishById = ({commit}, listDish) => {
  let user_token = cookies.get('user_token');
  return dish.deleteDish(user_token, listDish);
}

//Category
export const getAllCategories = ({commit}) => {
  let user_token = cookies.get('user_token');
  return category.getAll(user_token)
}
export const getPaginationCategories = ({commit}, page) => {
  let user_token = cookies.get('user_token');
  return category.getPagination(user_token, page)
}

export const addNewCategory = ({commit}, categoryData) => {
  let user_token = cookies.get('user_token');
  return category.addNew(user_token, {categoryData});
}

export const editCategoryById = ({commit}, categoryData) => {
  let user_token = cookies.get('user_token');
  return category.editById(user_token, {categoryData});
}

export const deleteCategoryById = ({commit}, categoryId) => {
  let user_token = cookies.get('user_token');
  return category.deleteById(user_token, categoryId);
}

//Option
export const getAllOptions = ({commit}) => {
  let user_token = cookies.get('user_token');
  return option.getAll(user_token)
}
export const getPaginationOptions = ({commit}, page) => {
  let user_token = cookies.get('user_token');
  return option.getPagination(user_token, page)
}

export const getOptionById = ({commit}, optionId) => {
  let user_token = cookies.get('user_token');
  return option.getById(user_token, optionId)
}

export const addNewOption = ({commit}, optionData) => {
  let user_token = cookies.get('user_token');
  return option.addNew(user_token, optionData)
}

export const editOptionById = ({commit}, optionData) => {
  let user_token = cookies.get('user_token');
  return option.editById(user_token, {optionData});
}

export const deleteOptionById = ({commit}, optionId) => {
  let user_token = cookies.get('user_token');
  return option.deleteById(user_token, optionId);
}

//Material
export const getAllMaterial = ({commit}) => {
  let user_token = cookies.get('user_token');
  return material.getAll(user_token);
}

export const searchAllMaterial = ({commit}, {id, name, page}) => {
  let user_token = cookies.get('user_token');
  return material.searchAll(user_token, {id, name, page});
}

export const getMaterialById = ({commit}, materialId) => {
  let user_token = cookies.get('user_token');
  return material.getById(user_token, materialId);
}

export const editMaterialById = ({commit}, materialData) => {
  let user_token = cookies.get('user_token');
  return material.editById(user_token, materialData);
}

export const getImportMaterialDetail = ({commit}, id) => {
  let user_token = cookies.get('user_token');
  return material.getImportMaterialDetail(user_token, id);
}

export const getMaterialReportDetail = ({commit}, id) => {
  let user_token = cookies.get('user_token');
  return material.getReportDetail(user_token, id);
}


//Import
export const getAllInventory = ({commit}) => {
  let user_token = cookies.get('user_token');
  return inventory.getAll(user_token)
}

export const searchAllImport = ({commit}, {id, dateFrom, dateTo, page}) => {
  let user_token = cookies.get('user_token');
  return inventory.searchImport(user_token, {id, dateFrom, dateTo, page});
}

export const insertImportInventory = ({commit}, {inventoryData}) => {
  let user_token = cookies.get('user_token');
  return inventory.insertInventory(user_token, {inventoryData})
}

export const insertImportExistInventory = ({commit}, inventoryData) => {
  let user_token = cookies.get('user_token');
  return inventory.insertExistInventory(user_token, inventoryData)
}

//Group Material
export const getAllGroupMaterial = ({commit}) => {
  let user_token = cookies.get('user_token');
  return groupMaterial.getAll(user_token)
}

//Supplier
export const getAllSupplier = ({commit}) => {
  let user_token = cookies.get('user_token');
  return supplier.getAll(user_token)
}

//Warehouse
export const getAllWarehouse = ({commit}) => {
  let user_token = cookies.get('user_token');
  return warehouse.getAll(user_token)
}

// Inventory Material
export const getAllInventoryMaterial = ({commit}) => {
  let user_token = cookies.get('user_token');
  return inventoryMaterial.getAll(user_token);
}

export const addNewInventoryMaterial = ({commit}, inventoryData) => {
  let user_token = cookies.get('user_token');
  return inventoryMaterial.addNew(user_token, inventoryData);
}


// Imgur API
export const uploadImageToImgur = ({commit}, imageUrl) => {
  return imgurAPI.upload(imageUrl)
}
