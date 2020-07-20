import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import { useDispatch } from 'react-redux'
import { changeAmountOrdering } from './../../actions/dishOrdering'


function DishOptionItem({ dishOption }) {
    return (
        <View style={{ flexDirection: 'row', marginHorizontal: 55, marginBottom: 2 }}>
            <Text style={{ marginLeft: 3 }}>{dishOption.quantity}</Text>
            <Text style={{ flex: 1, marginLeft: 10 }}>{dishOption.optionName}</Text>
            {dishOption.optionType == "MONEY" &&
                <Text style={{ color: 'red', marginRight: 3 }}>
                    {`${new Intl.NumberFormat().format(dishOption.optionPrice)} đ`}
                </Text>}
        </View>
    )
}

export default function OrderedItem({ item, showToppingBox }) {

    let heightCaculate = item.orderDishOptions.length != 0 ? 50 + (item.orderDishOptions.filter(option => option.quantity > 0).length * 22) : 50
    heightCaculate = item.comment != "" ? heightCaculate + 22 : heightCaculate

    const dispatch = useDispatch()
    function handleChangeValue(value) {
        const valueDish = {
            codeCheck: item.codeCheck,
            value: value,
            sellPrice: item.sellPrice * value
        }
        const action = changeAmountOrdering(valueDish)
        dispatch(action)
    }

    return (
        <View style={[styles.container, { height: heightCaculate }]}>
            <View style={{
                flex: 1,
                flexDirection: 'row',
                height: 50,
                backgroundColor: '#f2f2f2',
                borderColor: 'gray',
                borderWidth: 0.5,
                marginBottom: 5,
            }}>
                <TouchableOpacity
                    onPress={() => handleChangeValue(-1)}
                    style={styles.button}
                >
                    <Text style={styles.textButton}>-</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.infoDish} onLongPress={() => showToppingBox(item)}>
                    <Text numberOfLines={1} style={{ fontSize: 16, fontWeight: '700' }}>{item.dish.dishName}</Text>
                    <View style={{ flexDirection: 'row' }}>
                        <Text style={{ fontWeight: '600', fontSize: 16 }}>{item.quantity}</Text>
                        <Text style={{ marginHorizontal: 5 }}>x</Text>
                        <Text style={{ fontSize: 15, color: 'red' }}>{`${new Intl.NumberFormat().format(item.sellPrice)} đ`}</Text>
                    </View>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={() => handleChangeValue(1)}
                    style={styles.button}
                >
                    <Text style={styles.textButton}>+</Text>
                </TouchableOpacity>
            </View>

            <View style={{ flexDirection: 'column' }}>
                {
                    item.orderDishOptions.filter(option => option.quantity > 0).map(dishOption => {
                        return <DishOptionItem dishOption={dishOption} key={dishOption.optionId} />
                    })
                }
                {item.comment != "" && <Text style={{ height: 22 }}>{`- ${item.comment}`}</Text>}
                {
                    (item.codeCheck.length > 1 || !(item.comment == null || item.comment == ""))
                    && <View style={{ borderBottomColor: 'gray', borderBottomWidth: 0.5 }}></View>
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

    button: {
        flex: 1,
        backgroundColor: 'gray',
        justifyContent: "center",
        alignItems: 'center'
    },
    textButton: {
        color: 'white',
        fontSize: 45,
        textAlign: 'center'
    },
    infoDish: {
        flex: 6,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: "center",
        backgroundColor: '#f2f2f2',
        paddingHorizontal: 8
    }
})
