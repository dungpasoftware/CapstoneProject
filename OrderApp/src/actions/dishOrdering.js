export const addNewDish = (payload) => {
    return {
        type: 'ADD_NEW_DISH',
        payload
    }
}
export const changeAmountOrdering = (payload) => {
    return {
        type: 'CHANGE_AMOUNT_ORDERING',
        payload
    }
}