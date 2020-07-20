

export const getResult = (state) => {
  return state.number
}

export const getUserdata = (state) => {
  return state.userData
}

export const getUserName = (state) => {
  return ( state.userData !== null && state.userData.userName !== null) ? state.userData.userName : null
}

export const getAllTable = (state) => {
  return state.tables
}


// Socket
export const getSocketConnection = (state) => {
  return state.socketConnection
}
