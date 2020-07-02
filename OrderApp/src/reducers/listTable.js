import { LOAD_TABLE, LOAD_TABLE_SUCCESS, LOAD_TABLE_FAILURE } from "../common/actionType";

const initData = {
    loadSuccess: false,
    error: '',
    isLoading: false,
    listTable: []
};

const listTableReducer = (state = initData, { type, payload }) => {
    console.log(`loginReducer type: ${type} with payload: ${payload}`);
    switch (type) {
        case LOAD_TABLE:
            return {
                ...state,
                isLoading: true,
            };
        case LOAD_TABLE_SUCCESS:
            return {
                ...state,
                listTable: payload.access_token,
                loadSuccess: true,
                isLoading: false,
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