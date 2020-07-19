import { ADD_NEW_DISH, CHANGE_AMOUNT_ORDERING, CREATE_NEW_ORDER, CREATE_ORDER_FAILURE, LOAD_ORDER_INFOMATION, SAVE_ORDER, SAVE_ORDER_SUCCESS, SAVE_ORDER_FAILURE, CHANGE_OPTION_DISH_ORDERING, CHANGE_TOTAL_AP_ORDERING } from "../common/actionType";

const initialState = {
    rootOrder: {
        orderId: '',
        orderCode: '',
        statusId: '',
        tableId: '',
        totalAmount: 0,
        totalItem: 0,
        orderDish: [],
    },
    createOrderIsLoading: false,
    saveOrderIsLoding: false,
    error: '',
}

const dishOrderingReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_NEW_DISH: {
            let newRootOrder = { ...state.rootOrder }
            if (newRootOrder.totalItem == 0) {
                newRootOrder.orderDish.push(action.payload)
            } else {
                let haveSameDish = false;
                let codeCheck = action.payload.codeCheck;
                newRootOrder.orderDish = newRootOrder.orderDish.map(dish => {
                    if (dish.codeCheck === codeCheck) {
                        haveSameDish = true
                        return {
                            ...dish,
                            quantity: dish.quantity + 1,
                            sumPrice: dish.sumPrice + action.payload.sellPrice,
                        }
                    } else {
                        return dish
                    }
                })
                !haveSameDish && newRootOrder.orderDish.push(action.payload)

            }
            newRootOrder = {
                ...newRootOrder,
                totalItem: newRootOrder.totalItem + action.payload.quantity,
                totalAmount: newRootOrder.totalAmount + action.payload.sumPrice,
                orderDish: [...newRootOrder.orderDish]
            }
            return {
                ...state,
                rootOrder: { ...newRootOrder }
            }
        };
        case CHANGE_AMOUNT_ORDERING: {
            let newRootOrder = { ...state.rootOrder }
            let codeCheck = action.payload.codeCheck;
            let dishNeedDelete = -1;
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                if (dish.codeCheck === codeCheck) {
                    if (dish.quantity + action.payload.value <= 0) {
                        dishNeedDelete = index;
                    }
                    return {
                        ...dish,
                        quantity: dish.quantity + action.payload.value,
                        sumPrice: dish.sumPrice + action.payload.sellPrice,
                    }
                } else {
                    return dish
                }
            })
            dishNeedDelete != -1 && newRootOrder.orderDish.splice(dishNeedDelete, 1)
            newRootOrder = {
                ...newRootOrder,
                totalItem: newRootOrder.totalItem + action.payload.value,
                totalAmount: newRootOrder.totalAmount + action.payload.sellPrice,
                orderDish: [...newRootOrder.orderDish]
            }
            return {
                ...state,
                rootOrder: { ...newRootOrder }
            }
        };
        case CHANGE_OPTION_DISH_ORDERING: {
            let newRootOrder = { ...state.rootOrder }
            const { newDishOrder } = action.payload
            let orderDishId = newDishOrder.orderDishId;
            let oldSumPrice = 0
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                if (dish.orderDishId === orderDishId) {
                    oldSumPrice = dish.sumPrice
                    return {
                        ...newDishOrder
                    }
                } else {
                    return dish
                }
            })
            newRootOrder = {
                ...newRootOrder,
                totalAmount: newRootOrder.totalAmount - oldSumPrice + newDishOrder.sumPrice,
                orderDish: [...newRootOrder.orderDish]
            }
            return {
                ...state,
                rootOrder: { ...newRootOrder }
            }
        }
        case CREATE_NEW_ORDER: {
            return {
                ...state,
                createOrderIsLoading: true,
            }
        };
        case LOAD_ORDER_INFOMATION: {
            return {
                ...state,
                createOrderIsLoading: false,
                rootOrder: {
                    ...state.rootOrder,
                    orderId: action.payload.orderId,
                    orderCode: action.payload.orderCode,
                    statusId: action.payload.statusId,
                    tableId: action.payload.tableId,
                    totalAmount: action.payload.totalAmount != null ? action.payload.totalAmount : 0,
                    totalItem: action.payload.totalItem != null ? action.payload.totalItem : 0
                }

            }
        };
        case CREATE_ORDER_FAILURE: {
            return {
                ...state,
                createOrderSuccess: false,
                createOrderIsLoading: false,
                error: ''
            }
        };

        case SAVE_ORDER: {
            return {
                ...state,
                saveOrderIsLoding: true,
            }
        };
        case SAVE_ORDER_SUCCESS: {
            return {
                ...state,
                rootOrder: {
                    ...state.rootOrder,
                    orderDish: [],
                    orderDishOptions: []
                },
                saveOrderIsLoding: false,
            }
        };
        case SAVE_ORDER_FAILURE: {
            return {
                ...state,
                saveOrderIsLoding: false,
                error: 'abc'
            }
        }
        case CHANGE_TOTAL_AP_ORDERING: {
            return {
                ...state,
                rootOrder: {
                    ...state.rootOrder,
                    totalAmount: action.payload.totalAmount,
                    totalItem: action.payload.totalItem
                },
            }
        };
        default:
            return state;
    }
}

export default dishOrderingReducer