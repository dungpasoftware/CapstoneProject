import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE, HANDLE_LOGOUT, CHECK_TOKEN, CHECK_TOKEN_FAILURE } from "../common/actionType";

const initData = {
    userInfo: {
        accessToken: '',
        staffId: '',
        role: '',
    },
    isLoading: false,
    authenticated: false,
    status: 0,
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
                status: 0,
                authenticated: false,

            };
        case HANDLE_LOGIN:
            return {
                ...state,
                status: 0,
                isLoading: true,
            };


        case CHECK_TOKEN:
            return {
                ...state,
                status: 0,
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
                status: 200,
                authenticated: true,
                isLoading: false,
            };
        case LOGIN_FAILURE:
            return {
                ...state,
                authenticated: false,
                isLoading: false,
                status: payload.status,
            };
        case CHECK_TOKEN_FAILURE:
            return {
                ...state,
                authenticated: false,
                isLoading: false,
                status: 0,
            }
        default:
            return state;
    }
};
export default loginReducer;