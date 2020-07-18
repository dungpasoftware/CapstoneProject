import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default function DishReturnComponent({ item }) {
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
            <TouchableOpacity
                style={{
                    height: 50, flex: 1,
                    flexDirection: 'row',
                    alignItems: 'center',
                }}>
                <Text style={{ marginHorizontal: 8, fontSize: 20, fontWeight: '500' }}>2</Text>
                <Text style={{ flex: 1, fontSize: 18, marginLeft: 10 }}>{item.dish.dishName}</Text>
                <View style={{ flexDirection: "row", marginHorizontal: 8, alignItems: 'center' }}>
                    <TouchableOpacity>
                        <Feather name="minus-circle" color='red' size={40} />
                    </TouchableOpacity>
                    <Text style={{ textAlign: "center", marginHorizontal: 8, fontSize: 20, fontWeight: '500' }}>2</Text>
                    <TouchableOpacity>
                        <Feather name="plus-circle" color='green' size={40} />
                    </TouchableOpacity>
                </View>
            </TouchableOpacity >
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
