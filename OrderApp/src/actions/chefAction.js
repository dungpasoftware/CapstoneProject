import { LOAD_ALL_ORDER, LOAD_ALL_ORDER_SUCCESS, SOCKET_LOAD_ALL_ORDER } from "../common/actionType"

export const loadAllOrder = (payload) => {
    return {
        type: LOAD_ALL_ORDER,
        payload
    }
}

export const socketLoadAllOrder = (payload) => {
    return {
        type: SOCKET_LOAD_ALL_ORDER,
        payload
    }
}
export const loadAllOrderSuccess = (payload) => {
    return {
        type: LOAD_ALL_ORDER_SUCCESS,
        payload
    }
}
export const loadAllOrderFailure = (payload) => {
    return {
        type: LOAD_ALL_ORDER_FAILURE,
        payload
    }
}