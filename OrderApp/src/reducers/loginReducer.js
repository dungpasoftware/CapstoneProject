import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE } from "../common/actionType";

const initData = {
    accessToken: '',
    isLoading: false,
    authenticated: false,
    error: '',
};

const loginReducer = (state = initData, { type, payload }) => {
    console.log(`loginReducer type: ${type} with payload: ${payload}`);
    switch (type) {
        case HANDLE_LOGIN:
            return {
                ...state,
                isLoading: true,
            };
        case LOGIN_SUCCESS:
            return {
                ...state,
                accessToken: payload.access_token,
                authenticated: true,
                isLoading: false,
                error: '',
            };
        case LOGIN_FAILURE:
            return {
                ...state,
                authenticated: false,
                isLoading: false,
                error: payload.err,
            };
        default:
            return state;
    }
};
export default loginReducer;