import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default function DishReturnComponent({ item, handleChangeAmount, index }) {
    let isHaveDescription = item.orderDishOptions.length > 0
    const getDescriptionDish = () => {
        let description = item.orderDishOptions.reduce((accumulator, currentValue, currentIndex, array) => {
            let newDescription = currentValue.optionType == "MONEY" ?
                `${currentValue.optionName}: ${currentValue.quantity}` :
                `${currentValue.optionName}`
            newDescription += currentIndex < array.length - 1 ? ', ' : ''
            return accumulator + newDescription
        }, '- ')
        return description
    }

    return (
        <View style={styles.container}>
            <View
                style={{
                    height: 50, flex: 1,
                    flexDirection: 'row',
                    alignItems: 'center',
                }}>
                <Text style={{ width: 45, fontSize: 20, fontWeight: '500' }}>
                    {item.quantityOk}
                </Text>
                <Text
                    numberOfLines={1}
                    style={{ flex: 7, fontSize: 18, marginLeft: 5 }}>{item.dish.dishName}</Text>
                <View style={{ flex: 3.3, flexDirection: "row", alignItems: 'center' }}>
                    <TouchableOpacity
                        onPress={() => handleChangeAmount(index, item.orderDishId, 'sub', -1)}>
                        <Feather name="minus-circle" color='red' size={38} />
                    </TouchableOpacity>
                    <Text style={{ textAlign: "center", fontSize: 20, fontWeight: '500', flex: 1 }}>{item.quantityReturn}</Text>
                    <TouchableOpacity
                        onPress={() => handleChangeAmount(index, item.orderDishId, 'add', 1)}>
                        <Feather name="plus-circle" color='green' size={38} />
                    </TouchableOpacity>
                </View>
            </View >
            {isHaveDescription && <Text>{getDescriptionDish()}</Text>}
        </View >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        marginHorizontal: 10,
        flexDirection: 'column',
        borderBottomColor: 'gray',
        borderBottomWidth: 1

    }
})
