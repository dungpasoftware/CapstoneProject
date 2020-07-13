import React from 'react'
import { useDispatch } from 'react-redux'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'
import { addNewDish } from './../../actions/dishOrdering'

export default function DishItem({ item, showToppingBox }) {

    const dispatch = useDispatch();

    const handleTouchADish = () => {
        const newDishOrder = {
            orderDishId: gennerateKey(item.dishId),
            quantity: 1,
            sellPrice: item.defaultPrice,
            dish: {
                dishId: item.dishId,
                dishName: item.dishName,
                dishUnit: item.dishUnit,
                defaultPrice: item.defaultPrice,
            }
        }
        const action = addNewDish(newDishOrder)
        dispatch(action)
    }

    gennerateKey = (number) => {
        return Math.floor((Math.random() * 100000) + 1) + number;
    }

    return (
        <View style={styles.container}>
            <TouchableOpacity
                style={styles.touchable}
                onPress={handleTouchADish}
                onLongPress={() => showToppingBox(item)}
            >
                <Image style={{ height: 50, width: 50 }} source={require('./../../assets/dish.png')} />
                <View style={styles.infoDish}>
                    <Text numberOfLines={1} style={{ fontSize: 17, fontWeight: '600', marginBottom: 10 }}>{item.dishName}</Text>
                    <Text style={{ color: 'red' }}>{`${new Intl.NumberFormat().format(item.defaultPrice)} đồng`}</Text>
                </View>
                <TouchableOpacity>
                    <Image style={{ width: 40, height: 40 }} source={require('./../../assets/info.png')} />
                </TouchableOpacity>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        height: 70,

    },
    touchable: {
        flex: 1,
        flexDirection: "row",
        alignItems: 'center',
        marginHorizontal: 8
    },
    infoDish: {
        flex: 1,
        flexDirection: 'column',
        marginHorizontal: 8
    }
})
