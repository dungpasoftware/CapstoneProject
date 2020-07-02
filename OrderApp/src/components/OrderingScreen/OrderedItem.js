import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import { useDispatch } from 'react-redux'
import { changeAmountOrdering } from './../../actions/dishOrdering'

export default function OrderedItem({ item, showToppingBox }) {
    const dispatch = useDispatch()

    function handleChangeValue(value) {
        const valueDish = {
            id: item.id,
            value: value
        }
        const action = changeAmountOrdering(valueDish)
        dispatch(action)
    }
    return (
        <View style={styles.container}>
            <TouchableOpacity
                onPress={() => handleChangeValue(-1)}
                style={styles.button}
            >
                <Text style={styles.textButton}>-</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.infoDish} onLongPress={showToppingBox}>
                <Text numberOfLines={1} style={{ fontSize: 16, fontWeight: '700' }}>{item.name}</Text>
                <View style={{ flexDirection: 'row' }}>
                    <Text style={{ fontWeight: '600', fontSize: 16 }}>{item.amount}</Text>
                    <Text style={{ marginHorizontal: 5 }}>x</Text>
                    <Text style={{ fontSize: 15, color: 'red' }}>{`${item.price} đ`}</Text>
                </View>
            </TouchableOpacity>
            <TouchableOpacity
                onPress={() => handleChangeValue(1)}
                style={styles.button}
            >
                <Text style={styles.textButton}>+</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        height: 50,
        borderColor: 'gray',
        borderWidth: 0.5,
        marginBottom: 2
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
