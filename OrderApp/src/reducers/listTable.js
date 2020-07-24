import { LOAD_TABLE, LOAD_TABLE_SUCCESS, LOAD_TABLE_FAILURE, SOCKET_LOAD_TABLE } from "../common/actionType";

const initData = {
    loadSuccess: false,
    error: '',
    isLoading: false,
    listTable: [],
    listLocation: []
};

const listTableReducer = (state = initData, { type, payload }) => {
    switch (type) {
        case LOAD_TABLE:
            return {
                ...state,
                isLoading: true,
            };
        case LOAD_TABLE_SUCCESS:
            return {
                ...state,
                listLocation: payload.listLocation,
                listTable: payload.listTable,
                loadSuccess: true,
                isLoading: false,
                error: '',
            };
        case SOCKET_LOAD_TABLE:
            return {
                ...state,
                listTable: payload.listTable,
                error: '',
            };
        case LOAD_TABLE_FAILURE:
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
export default listTableReducer;