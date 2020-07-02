const initialState = {
    id: '1',
    listDish: []
}

const dishOrderingReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'ADD_NEW_DISH': {
            let newList = [...state.listDish]
            if (newList.length == 0) {
                newList.push(action.payload)
            } else {
                let haveSameDish = false;
                let dishId = action.payload.dishId;
                newList = newList.map(dish => {
                    if (dish.dishId === dishId) {
                        haveSameDish = true
                        return {
                            id: dish.id,
                            dishId: dishId,
                            name: dish.name,
                            amount: dish.amount + 1,
                            price: dish.price
                        }
                    } else {
                        return dish
                    }
                })
                !haveSameDish && newList.push(action.payload)
            }
            return {
                ...state,
                listDish: newList
            }
        }
        case 'CHANGE_AMOUNT_ORDERING': {
            let newList = [...state.listDish];
            let id = action.payload.id
            let dishNeedDelete = -1;
            newList = newList.map((dish, index) => {
                if (dish.id === id) {
                    if (dish.amount + action.payload.value <= 0) {
                        dishNeedDelete = index;
                    }
                    return {
                        id: dish.id,
                        dishId: dish.dishId,
                        name: dish.name,
                        amount: dish.amount + action.payload.value,
                        price: dish.price
                    }
                } else {
                    return dish
                }
            })
            dishNeedDelete != -1 && newList.splice(dishNeedDelete, 1)
            return {
                ...state,
                listDish: newList
            }
        }
        default:
            return state
    }
}

export default dishOrderingReducer