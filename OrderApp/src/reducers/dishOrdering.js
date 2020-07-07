import { ADD_NEW_DISH, CHANGE_AMOUNT_ORDERING } from "../common/actionType";

const initialState = {
    id: '1',
    orderDish: []
}

const dishOrderingReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_NEW_DISH: {
            let newList = [...state.orderDish]
            if (newList.length == 0) {
                newList.push(action.payload)
            } else {
                let haveSameDish = false;
                let dishId = action.payload.dish.dishId;
                newList = newList.map(dish => {
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
                !haveSameDish && newList.push(action.payload)
            }
            return {
                ...state,
                orderDish: newList
            }
        }
        case CHANGE_AMOUNT_ORDERING: {
            let newList = [...state.orderDish];
            let id = action.payload.dishId
            let dishNeedDelete = -1;
            newList = newList.map((dish, index) => {
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
            dishNeedDelete != -1 && newList.splice(dishNeedDelete, 1)
            return {
                ...state,
                orderDish: newList
            }
        }
        default:
            return state
    }
}

export default dishOrderingReducer