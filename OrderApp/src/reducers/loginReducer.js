import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE, HANDLE_LOGOUT, CHECK_TOKEN } from "../common/actionType";

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


        case CHECK_TOKEN:
            return {
                ...state,
                isLoading: true,
                authenticated: false,
            };

        case LOGIN_SUCCESS:
            return {
                ...state,
                userInfo: {
                    accessToken: payload.token,
                    staffCode: payload.staffCode,
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