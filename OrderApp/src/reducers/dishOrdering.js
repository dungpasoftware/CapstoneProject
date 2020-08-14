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
    error: null,
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
            let newSellPrice = action.payload.sellPrice
            let newQuantity = action.payload.value
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                if (dish.codeCheck === codeCheck) {
                    let newNotEnoughMaterial = dish.notEnoughMaterial
                    newSellPrice = newSellPrice * newQuantity
                    if (dish.quantity + newQuantity <= 0) {
                        dishNeedDelete = index;
                        newQuantity = dish.quantity * -1
                        newSellPrice = dish.quantity * action.payload.sellPrice * -1
                    }
                    if (action.payload.type == 'sub' && dish.notEnoughMaterial == true) {
                        newNotEnoughMaterial = false
                    }
                    return {
                        ...dish,
                        notEnoughMaterial: newNotEnoughMaterial,
                        quantity: dish.quantity + newQuantity,
                        sumPrice: dish.sumPrice + newSellPrice,
                    }
                } else {
                    return dish
                }
            })
            dishNeedDelete != -1 && newRootOrder.orderDish.splice(dishNeedDelete, 1)
            newRootOrder = {
                ...newRootOrder,
                totalItem: newRootOrder.totalItem + newQuantity,
                totalAmount: newRootOrder.totalAmount + newSellPrice,
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
            let codeCheck = newDishOrder.codeCheck;
            let oldSumPrice = 0
            let isExistCodeCheck = false
            let dishNeedDelete = -1
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                if (dish.orderDishId === orderDishId) {
                    dishNeedDelete = index
                }
                if (dish.codeCheck == codeCheck) {
                    oldSumPrice = dish.sumPrice
                    isExistCodeCheck = true;
                    return {
                        ...dish,
                        quantity: dish.quantity + 1,
                        sumPrice: dish.sumPrice + dish.sellPrice,

                    }
                } else {
                    return dish
                }

            })
            if (isExistCodeCheck && dishNeedDelete != -1) {
                newRootOrder.orderDish.splice(dishNeedDelete, 1)
            }
            newRootOrder.orderDish = newRootOrder.orderDish.map((dish) => {
                if (dish.orderDishId === orderDishId && isExistCodeCheck == false) {
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
                error: null
            }
        };

        case SAVE_ORDER: {
            return {
                ...state,
                saveOrderIsLoading: true,
                message: null,
                error: null
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
                error: null
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
            //! xu ly message
            let message = response.message
            let newMessage = ''
            newMessage = message.reduce((accumulator, currentValue) => {
                return accumulator.concat(currentValue).concat('\n')
            }, '')

            let messageMaterial = response.messageMaterial
            let newMessageMaterial = ""
            newMessageMaterial = messageMaterial.reduce((accumulator, currentValue, currentIndex) => {
                let newMaterialMessage = accumulator + currentValue
                if (currentIndex < messageMaterial.length - 1) newMaterialMessage += ', '
                return newMaterialMessage
            }, '')
            newMessage = newMessage + '- Thiếu: ' + newMessageMaterial
            console.log(newMessage)

            newRootOrder.orderDish = newRootOrder.orderDish.map((dish, index) => {
                let newDish = { ...dish }
                let dishNotEnough = false
                response.orderDish.forEach(oldDish => {
                    if (dish.orderDishId == oldDish.orderDishId) {
                        dishNotEnough = true
                        newDish = {
                            ...newDish,
                            notEnoughMaterial: true
                        }
                    }
                });
                if (!dishNotEnough) {
                    newDish = {
                        ...newDish,
                        notEnoughMaterial: false
                    }
                }
                return newDish
            })
            return {
                ...state,
                rootOrder: newRootOrder,
                saveOrderIsLoading: false,
                message: newMessage,
                error: null
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