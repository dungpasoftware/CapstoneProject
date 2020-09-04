import * as types from './mutations_type'

export const mutations = {
  [types.NUMBER_INCREMENT] (state, index) {
    state.number += index
  },
  [types.NUMBER_DECREMENT] (state, index) {
    state.number -= index
  },


  //Auth
  [types.LOGOUT] (state) {
    state.userData = null;
  },
  [types.SET_USERDATA_FROM_COOKIES] (state, userdata) {
    state.userData = userdata;
  },
  [types.OPEN_LOADING_CHECK_LOGNIN] (state) {
    state.loadingCheckLogin = true;
  },
  [types.CLOSE_LOADING_CHECK_LOGNIN] (state) {
    state.loadingCheckLogin = false;
  },
  [types.OPEN_LOADING] (state) {
    state.loader = true;
  },
  [types.CLOSE_LOADING] (state) {
    state.loader = false;
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

}
