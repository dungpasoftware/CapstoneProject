import {
    ADD_NEW_DISH, CHANGE_AMOUNT_ORDERING, CREATE_NEW_ORDER, LOAD_ORDER_INFOMATION,
    CREATE_ORDER_FAILURE, SAVE_ORDER, SAVE_ORDER_SUCCESS, SAVE_ORDER_FAILURE, CHANGE_OPTION_DISH_ORDERING
} from "../common/actionType"

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

export const saveOrder = payload => ({
    type: SAVE_ORDER,
    payload,
});

export const saveOrderSuccess = payload => ({
    type: SAVE_ORDER_SUCCESS,
    payload,
});

export const saveOrderFailure = payload => ({
    type: SAVE_ORDER_FAILURE,
    payload,
});

export const changeOptionDishOrdering = payload => ({
    type: CHANGE_OPTION_DISH_ORDERING,
    payload,
});