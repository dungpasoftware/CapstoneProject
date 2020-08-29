import { LOAD_ORDERED, LOAD_ORDERED_SUCCESS, LOAD_ORDERED_FAILURE } from "../common/actionType"

export const loadDishOrdered = (payload) => {
    return {
        type: LOAD_ORDERED,
        payload
    }
}
export const loadDishOrderedSuccess = (payload) => {
    return {
        type: LOAD_ORDERED_SUCCESS,
        payload
    }
}
export const loadDishOrderedFailure = (payload) => {
    return {
        type: LOAD_ORDERED_FAILURE,
        payload
    }
}