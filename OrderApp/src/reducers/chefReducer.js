import { LOAD_ALL_ORDER, LOAD_ALL_ORDER_SUCCESS, LOAD_ALL_ORDER_FAILURE, SOCKET_LOAD_ALL_ORDER } from "../common/actionType";

const initData = {
    loadSuccess: false,
    error: null,
    isLoading: false,
    listOrders: []
};

const chefReducer = (state = initData, { type, payload }) => {
    switch (type) {
        case LOAD_ALL_ORDER:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case LOAD_ALL_ORDER_SUCCESS:
            return {
                ...state,
                listOrders: payload,
                loadSuccess: true,
                isLoading: false,
                error: null,
            };
        case LOAD_ALL_ORDER_FAILURE:
            return {
                ...state,
                isLoading: false,
                loadSuccess: false,
                error: payload.err,
            };
        case SOCKET_LOAD_ALL_ORDER:
            return {
                ...state,
                listOrders: payload.listOrders,
            };
        default:
            return state;
    }
};
export default chefReducer;