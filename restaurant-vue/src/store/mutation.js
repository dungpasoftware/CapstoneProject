import * as types from './mutations_type'

export const mutations = {
  [types.NUMBER_INCREMENT] (state, index) {
    state.number += index
  },
  [types.NUMBER_DECREMENT] (state, index) {
    state.number -= index
  },
  [types.LOGIN] (state, userData) {
    state.userData = userData
  },
  [types.LOGOUT] (state) {
    state.userData = null
  }
}
