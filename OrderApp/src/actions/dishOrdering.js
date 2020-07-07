import { ADD_NEW_DISH, CHANGE_AMOUNT_ORDERING, CREATE_NEW_ORDER, LOAD_ORDER_INFOMATION, CREATE_ORDER_FAILURE } from "../common/actionType"

export const addNewDish = (payload) => {
    return {
        type: ADD_NEW_DISH,
        payload
    }
}
export const changeAmountOrdering = (payload) => {
    return {
        type: CHANGE_AMOUNT_ORDERING,
        payload
    }
}

export const createNewOrder = (payload) => {
    return {
        type: CREATE_NEW_ORDER,
        payload
    }
}

export const loadOrderInfomation = (payload) => {
    return {
        type: LOAD_ORDER_INFOMATION,
        payload
    }
}

export const createOrderFailure = (payload) => {
    return {
        type: CREATE_ORDER_FAILURE,
        payload
    }
}