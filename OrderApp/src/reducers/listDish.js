import { LOAD_DISH, LOAD_DISH_SUCCESS, LOAD_DISH_FAILURE } from "../common/actionType";

const initData = {
    loadSuccess: false,
    error: '',
    isLoading: false,
    listDish: []
};

const listDishReducer = (state = initData, { type, payload }) => {
    console.log(`listDish type: ${type} with payload: ${payload}`);
    switch (type) {
        case LOAD_DISH:
            return {
                ...state,
                isLoading: true,
            };
        case LOAD_DISH_SUCCESS:
            return {
                ...state,
                listDish: payload,
                loadSuccess: true,
                isLoading: false,
                error: '',
            };
        case LOAD_DISH_FAILURE:
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
export default listDishReducer;