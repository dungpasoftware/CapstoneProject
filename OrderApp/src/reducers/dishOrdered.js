import { LOAD_ORDERED, LOAD_DISH_SUCCESS, LOAD_ORDERED_FAILURE, LOAD_ORDERED_SUCCESS } from "../common/actionType";

const initData = {
    loadSuccess: false,
    error: '',
    isLoading: false,
    rootOrder: {}
};

const dishOrdered = (state = initData, { type, payload }) => {
    switch (type) {
        case LOAD_ORDERED:
            return {
                ...state,
                isLoading: true,
            };
        case LOAD_ORDERED_SUCCESS:
            return {
                ...state,
                rootOrder: payload,
                loadSuccess: true,
                isLoading: false,
                error: '',
            };
        case LOAD_ORDERED_FAILURE:
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
export default dishOrdered;