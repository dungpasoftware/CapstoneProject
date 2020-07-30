import { ADD_NEW_DISH, CHANGE_AMOUNT_ORDERING, CREATE_NEW_ORDER, CREATE_ORDER_FAILURE, LOAD_ORDER_INFOMATION, SAVE_ORDER, SAVE_ORDER_SUCCESS, SAVE_ORDER_FAILURE, CHANGE_OPTION_DISH_ORDERING, CHANGE_TOTAL_AP_ORDERING, CHANGE_TABLE_ID, SAVE_ORDER_NOT_ENOUGH } from "../common/actionType";

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
    saveOrderIsLoading: false,
    error: '',
    message: null
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
                    let newNotEnoughMaterial = dish.notEnoughMaterial
                    if (dish.quantity + action.payload.value <= 0) {
                        dishNeedDelete = index;
                    }
                    if (action.payload.value == -1 && dish.notEnoughMaterial == true) {
                        newNotEnoughMaterial = false
                    }
                    return {
                        ...dish,
                        notEnoughMaterial: newNotEnoughMaterial,
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
                    orderDish: [],
                    totalAmount: action.payload.totalAmount != null ? action.payload.totalAmount : 0,
                    totalItem: action.payload.totalItem != null ? action.payload.totalItem : 0
                }

            }
        };
        case CREATE_ORDER_FAILURE: {
            return {
                ...state,
                createOrderIsLoading: false,
                error: ''
            }
        };

        case SAVE_ORDER: {
            return {
                ...state,
                saveOrderIsLoading: true,
                message: null
            }
        };
        case SAVE_ORDER_SUCCESS: {
            return {
                ...state,
                rootOrder: {
                    ...state.rootOrder,
                    orderDish: [],
                },
                saveOrderIsLoading: false,
                error: ''
            }
        };
        case SAVE_ORDER_FAILURE: {
            return {
                ...state,
                saveOrderIsLoading: false,
                error: action.payload.err,
            };
        }

        case SAVE_ORDER_NOT_ENOUGH: {
            let newRootOrder = { ...state.rootOrder }
            const response = action.payload.response
            let message = response.message
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                let newDish = { ...dish }
                response.orderDish.forEach(oldDish => {
                    if (dish.orderDishId == oldDish.orderDishId) {
                        newDish = {
                            ...newDish,
                            notEnoughMaterial: true
                        }
                    }
                });
                return newDish
            })
            return {
                ...state,
                rootOrder: newRootOrder,
                saveOrderIsLoading: false,
                message: message
            }
        };
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
        case CHANGE_TABLE_ID: {
            return {
                ...state,
                rootOrder: {
                    ...state.rootOrder,
                    tableId: action.payload.tableId,
                },
            }
        };
        default:
            return state;
    }
}

export default dishOrderingReducer