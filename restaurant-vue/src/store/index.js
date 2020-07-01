import Vue from "vue";
import Vuex from "vuex";
import {mutations} from './mutation'
import * as actions from './actions'
import * as getters from './getters'

Vue.use(Vuex);

const state = {
  userData: null,
  number: 0
}

export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters
})
