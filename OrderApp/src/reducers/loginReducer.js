import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE, HANDLE_LOGOUT, CHECK_TOKEN, CHECK_TOKEN_FAILURE } from "../common/actionType";

const initData = {
    userInfo: {
        accessToken: '',
        staffId: '',
        role: '',
    },
    isLoading: false,
    authenticated: false,
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

            };
        case HANDLE_LOGIN:
            return {
                ...state,
                isLoading: true,
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
        case LOGIN_FAILURE:
            let newMessageServer = ''
            switch (payload.status) {
                case 401:
                    newMessageServer = payload.data.message
                    break;
                default:
                    newMessageServer = 'Có lỗi gì đó xảy ra!'
                    break;
            }
            return {
                ...state,
                authenticated: false,
                isLoading: false,
                messageServer: newMessageServer
            };
        case CHECK_TOKEN_FAILURE:
            let newMessage = 'Có gì đó xảy ra !'
            if (payload == 'timeout') {
                newMessage = 'Có gì đó xảy ra ! (timeout:3000)'
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