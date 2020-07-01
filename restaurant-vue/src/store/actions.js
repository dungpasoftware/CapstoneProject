import * as types from './mutations_type'

export const numberIncrement = ({commit}, index) => {
  commit(types.NUMBER_INCREMENT, index);
}

export const login = ({commit}, loginData) => {
  commit(types.LOGIN, loginData);
}

export const logout = ({commit}) => {
  commit(types.LOGOUT);
}
