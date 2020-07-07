import { ADD_NEW_DISH, CHANGE_AMOUNT_ORDERING, CREATE_NEW_ORDER, CREATE_ORDER_FAILURE, LOAD_ORDER_INFOMATION, SAVE_ORDER, SAVE_ORDER_SUCCESS, SAVE_ORDER_FAILURE } from "../common/actionType";

const initialState = {
    rootOrder: {
        orderId: '',
        orderCode: '',
        orderStatusId: '',
        tableId: '',
        totalAmount: 0,
        totalItem: 0,
        orderDish: [],
        orderDishOptions: []
    },
    isLoading: false,
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
                let dishId = action.payload.dish.dishId;
                newRootOrder.orderDish = newRootOrder.orderDish.map(dish => {
                    if (dish.dish.dishId === dishId) {
                        haveSameDish = true
                        return {
                            ...dish,
                            quantity: dish.quantity + 1,
                            sellPrice: dish.dish.defaultPrice * (dish.quantity + 1),
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
                totalAmount: newRootOrder.totalAmount + action.payload.sellPrice,
                orderDish: [...newRootOrder.orderDish]
            }
            return {
                ...state,
                rootOrder: { ...newRootOrder }
            }
        };
        case CHANGE_AMOUNT_ORDERING: {
            let newRootOrder = { ...state.rootOrder }
            let id = action.payload.dishId
            let dishNeedDelete = -1;
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                if (dish.dish.dishId === id) {
                    if (dish.quantity + action.payload.value <= 0) {
                        dishNeedDelete = index;
                    }
                    return {
                        ...dish,
                        quantity: dish.quantity + action.payload.value,
                        sellPrice: dish.dish.defaultPrice * (dish.quantity + action.payload.value),
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
        case CREATE_NEW_ORDER: {
            return {
                ...state,
                isLoading: true
            }
        };
        case LOAD_ORDER_INFOMATION: {
            return {
                ...state,
                isLoading: false,
                rootOrder: {
                    ...state.rootOrder,
                    orderId: action.payload.orderId,
                    orderCode: action.payload.orderCode,
                    orderStatusId: action.payload.orderStatusId,
                    tableId: action.payload.tableId,
                }

            }
        };
        case CREATE_ORDER_FAILURE: {
            return {
                ...state,
                isLoading: false,
                error: ''
            }
        };

        case SAVE_ORDER: {
            return {
                ...state,
                isLoading: true,
            }
        };
        case SAVE_ORDER_SUCCESS: {
            return {
                ...state,
                rootOrder: {
                    ...state.rootOrder,
                    totalAmount: 0,
                    totalItem: 0,
                    orderDish: [],
                    orderDishOptions: []
                },
                isLoading: false,
            }
        };
        case SAVE_ORDER_FAILURE: {
            return {
                ...state,
                isLoading: false,
                error: 'abc'
            }
        }
        default:
            return state;
    }
}

export default dishOrderingReducer