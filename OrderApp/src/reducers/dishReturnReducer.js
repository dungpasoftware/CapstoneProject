import { LOAD_ORDER_DISH_RETURN, LOAD_ORDER_DISH_RETURN_SUCCESS, LOAD_ORDER_DISH_RETURN_FAILURE } from "../common/actionType";

const initData = {
    loadSuccess: false,
    error: '',
    isLoading: false,
    listDishReturn: []
};

const dishReturnReducer = (state = initData, { type, payload }) => {
    switch (type) {
        case LOAD_ORDER_DISH_RETURN:
            return {
                ...state,
                isLoading: true,
            };
        case LOAD_ORDER_DISH_RETURN_SUCCESS:
            return {
                ...state,
                listDishReturn: payload,
                loadSuccess: true,
                isLoading: false,
                error: '',
            };
        case LOAD_ORDER_DISH_RETURN_FAILURE:
            return {
                ...state,
                isLoading: false,
                loadSuccess: false,
                error: payload.err,
            };
        default:
            return state;
    }
};
export default dishReturnReducer;