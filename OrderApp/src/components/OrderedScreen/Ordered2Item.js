import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'


function DishOptionItem({ dishOption }) {
    return (
        <View style={{ flexDirection: 'row', marginHorizontal: 15, marginBottom: 2 }}>
            <Text style={{ marginLeft: 3 }}>{dishOption.quantity}</Text>
            <Text style={{ flex: 1, marginLeft: 25 }}>{dishOption.optionName}</Text>
            {dishOption.optionType == "MONEY" &&
                <Text style={{ color: 'red', marginRight: 10 }}>
                    {`${new Intl.NumberFormat().format(dishOption.optionPrice)} đồng`}
                </Text>}
        </View>
    )
}


export default function Ordered2Item({ item, showOptionDish }) {
    let heightCaculate = 50 + (item.orderDishOptions.length * 22)
    return (
        <View style={[styles.container, { height: heightCaculate }]}>
            <TouchableOpacity style={{
                flex: 1,
                flexDirection: 'row',
                height: 50,
                backgroundColor: '#f2f2f2',
                borderColor: 'gray',
                borderWidth: 0.5,
                marginBottom: 5
            }} onPress={() => showOptionDish(item)}>
                <View
                    style={{
                        flex: 1,
                        justifyContent: 'center',
                        borderRightColor: 'gray',
                        borderRightWidth: 0.5,
                        alignItems: "center"
                    }}>
                    <Text style={styles.text}>{item.quantity}</Text>
                </View>
                <View
                    style={{
                        flex: 5,
                        justifyContent: 'center',
                        marginHorizontal: 8
                    }}>
                    <Text numberOfLines={1} style={styles.text}>{item.dish.dishName}</Text>
                </View>
                <View style={{ flex: 3, justifyContent: 'center' }}>
                    <Text
                        style={{ color: 'red', textAlign: 'center', fontSize: 16 }}
                        numberOfLines={1}
                    >
                        {`${new Intl.NumberFormat().format(item.sellPrice)} đồng`}
                    </Text>
                </View>
            </TouchableOpacity>
            <View style={{ flexDirection: 'column' }}>
                {
                    item.orderDishOptions.map(dishOption => {
                        return <DishOptionItem dishOption={dishOption} key={dishOption.orderDishOptionId} />
                    })
                }
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',


    },
    text: {
        fontSize: 16,
        fontWeight: '600',
    }

})
