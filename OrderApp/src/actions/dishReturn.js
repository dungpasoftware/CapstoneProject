import { LOAD_ORDER_DISH_RETURN, LOAD_ORDER_DISH_RETURN_SUCCESS, LOAD_ORDER_DISH_RETURN_FAILURE } from "../common/actionType"

export const loadOrderDishReturn = (payload) => {
    return {
        type: LOAD_ORDER_DISH_RETURN,
        payload
    }
}
export const loadOrderDishReturnSuccess = (payload) => {
    return {
        type: LOAD_ORDER_DISH_RETURN_SUCCESS,
        payload
    }
}
export const loadOrderDishReturnFailure = (payload) => {
    return {
        type: LOAD_ORDER_DISH_RETURN_FAILURE,
        payload
    }
}