import * as types from './mutations_type'

export const mutations = {
  [types.NUMBER_INCREMENT] (state, index) {
    state.number += index
  },
  [types.NUMBER_DECREMENT] (state, index) {
    state.number -= index
  },


  //Auth
  [types.LOGIN] (state, userName) {
    state.userData.userName = userName
  },
  [types.LOGOUT] (state) {
    state.userData.userName = null;
  },
  [types.SET_USERDATA_FROM_COOKIES] (state, userdata) {
    state.userToken = userdata.token;
  },


  //Table
  [types.SET_ALL_TABLE] (state, tables) {
    state.tables = tables
  },
  [types.CLEAR_ALL_TABLE] (state) {
    state.tables = null
  },


  //Location
  [types.SET_ALL_LOCATION_TABLE] (state, location) {
    state.table_locations = location
  },
  [types.CLEAR_ALL_LOCATION_TABLE] (state) {
    state.table_locations = null
  },


  //Socket
  [types.SET_SOCKET_CONNECTION] (state, connection) {
    statr.socketConnection = connection
  }
}
