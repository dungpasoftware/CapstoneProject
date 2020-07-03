import { LOAD_DISH, LOAD_DISH_SUCCESS, LOAD_DISH_FAILURE } from "../common/actionType";

export const loadDish = payload => ({
    type: LOAD_DISH,
    payload,
});

export const loadDishSuccess = payload => ({
    type: LOAD_DISH_SUCCESS,
    payload,
});

export const loadDishFailure = payload => ({
    type: LOAD_DISH_FAILURE,
    payload,
});

export default {
    loadDish,
    loadDishSuccess,
    loadDishFailure,
};