import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE, HANDLE_LOGOUT, CHECK_TOKEN, CHECK_TOKEN_FAILURE, CHECK_TOKEN_SUCCESS } from "../common/actionType";
import { ROLE_ORDER_TAKER, ROLE_CHEF } from "../common/roleType";

const initData = {
    userInfo: {
        accessToken: '',
        staffId: '',
        role: '',
    },
    isLoading: false,
    authenticated: false,
    isLoadingLogin: false,
    messageServer: '',
    errorMessageToken: ''
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
                messageServer: '',

            };
        case HANDLE_LOGIN:
            return {
                ...state,
                isLoadingLogin: true,
                messageServer: ''
            };


        case CHECK_TOKEN:
            return {
                ...state,
                isLoading: true,
                authenticated: false,
                errorMessageToken: ''
            };

        case LOGIN_SUCCESS:
            if (payload.roleName == ROLE_ORDER_TAKER || payload.roleName == ROLE_CHEF) {
                return {
                    ...state,
                    userInfo: {
                        accessToken: payload.token,
                        staffCode: payload.staffCode,
                        staffId: payload.staffId,
                        role: payload.roleName,
                    },
                    isLoadingLogin: false,
                    messageServer: 'pass200',
                };
            } else {
                return {
                    ...state,
                    isLoadingLogin: false,
                    messageServer: "Sai tài khoản hoặc mật khẩu"
                }
            }


        case LOGIN_FAILURE:
            let newMessageServer = ''
            switch (payload.response.status) {
                case 401:
                    newMessageServer = "Sai tài khoản hoặc mật khẩu"
                    break;
                default:
                    newMessageServer = 'Có gì đó xảy ra.'
                    break;
            }
            return {
                ...state,
                isLoadingLogin: false,
                messageServer: newMessageServer
            };
        case CHECK_TOKEN_SUCCESS:
            if (payload.roleName == ROLE_ORDER_TAKER || payload.roleName == ROLE_CHEF) {
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
                    messageServer: 'pass200',
                };
            } else {
                return {
                    ...state,
                    authenticated: false,
                    isLoading: false,
                    errorMessageToken: "Có gì đó xảy ra !"
                }
            }
        case CHECK_TOKEN_FAILURE:
            let newMessage = 'Có gì đó xảy ra !'
            if (payload == 'timeout') {
                newMessage = 'Hệ thống không phản hồi!'
            }
            return {
                ...state,
                authenticated: false,
                isLoading: false,
                errorMessageToken: newMessage
            }
        default:
            return state;
    }
};
export default loginReducer;