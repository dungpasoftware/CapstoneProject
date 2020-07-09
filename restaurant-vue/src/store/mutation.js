import * as types from './mutations_type'

export const mutations = {
  [types.NUMBER_INCREMENT] (state, index) {
    state.number += index
  },
  [types.NUMBER_DECREMENT] (state, index) {
    state.number -= index
  },


  //Auth
  [types.LOGIN] (state, userData) {
    state.userData = userData
  },
  [types.LOGOUT] (state) {
    state.userData = null
  },
  [types.SET_USERDATA_FROM_COOKIES] (state, userdata) {
    state.userToken = userdata.token;
  },


  //Cachier
  [types.GET_ALL_TABLE] (state, tables) {
    state.tables = tables
  },
  [types.GET_ALL_LOCATION_TABLE] (state, location) {
    state.table_locations = location
  }
}
