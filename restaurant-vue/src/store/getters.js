

export const getResult = (state) => {
  return state.number
}

export const getStaffCode = (state) => {
  return ( state.userData !== null && state.userData.staffCode !== null) ? state.userData.staffCode : null
}

export const getRoleName = (state) => {
  return ( state.userData !== null && state.userData.roleName !== null) ? state.userData.roleName : null
}

export const getAllTable = (state) => {
  return state.tables
}
