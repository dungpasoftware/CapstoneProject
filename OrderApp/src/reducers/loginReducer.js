import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE, HANDLE_LOGOUT } from "../common/actionType";

const initData = {
    userInfo: {
        accessToken: '',
        staffId: '',
        role: '',
    },
    isLoading: false,
    authenticated: false,
    error: '',
};

const loginReducer = (state = initData, { type, payload }) => {
    console.log(`loginReducer type: ${type} with payload: ${payload}`);
    switch (type) {
        case HANDLE_LOGOUT:
            return {
                ...state,
                userInfo: {
                    accessToken: '',
                    staffId: '',
                    role: '',
                },
                authenticated: false,

            };
        case HANDLE_LOGIN:
            return {
                ...state,
                isLoading: true,
            };
        case LOGIN_SUCCESS:
            return {
                ...state,
                userInfo: {
                    accessToken: payload.token,
                    staffId: payload.staffId,
                    role: payload.roleName,
                },
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