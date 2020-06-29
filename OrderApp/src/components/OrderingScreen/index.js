import React from 'react'
import { StyleSheet, Text, View, Image } from 'react-native'
import { FlatList, TouchableOpacity } from 'react-native-gesture-handler'
import dataDisher from '../dataDisher'
import dataCategory from '../dataCategory'
import OrderedItem from './OrderedItem'
import CategoryItem from './CategoryItem'
import DishItem from './DishItem'

export default function OrderingScreen() {
    return (
        <View style={styles.container}>
            <View style={styles.orderedContainer}>
                <FlatList
                    data={dataDisher}
                    renderItem={({ item, index }) => {
                        return (
                            <OrderedItem item={item} index={index} />
                        )
                    }}
                />
            </View>
            <View style={styles.billInfoContainer}>
                <View style={{ flex: 1, flexDirection: "row", alignItems: "center" }}>
                    <Image style={{ height: 30, width: 30, marginHorizontal: 8 }} source={require('./../../assets/contract.png')} />
                    <Text style={{ fontWeight: '600', fontSize: 16 }}>2</Text>
                </View>
                <View style={{ flex: 5, flexDirection: "row", alignItems: "center", marginLeft: 8 }} >
                    <Image style={{ height: 30, width: 30, marginHorizontal: 8 }} source={require('./../../assets/dollar.png')} />
                    <Text style={{ color: 'red', fontWeight: '600', fontSize: 16 }}>199.999 d</Text>
                </View>
                <TouchableOpacity
                    style={styles.touchInfo}>
                    <Text style={{ fontWeight: 'bold', fontSize: 18 }}>Lưu</Text>
                </TouchableOpacity>
            </View>
            <View style={styles.orderDishContainer}>
                <View style={styles.categoryList}>
                    <FlatList
                        data={dataCategory}
                        renderItem={({ item, index }) => {
                            return (
                                <CategoryItem item={item} index={index} />
                            )
                        }}
                    />
                </View>
                <View style={{ flex: 9 }}>
                    <FlatList
                        data={dataDisher}
                        renderItem={({ item, index }) => {
                            return (
                                <DishItem item={item} index={index} />
                            )
                        }}
                    />
                </View>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column'
    },
    orderedContainer: {
        flex: 8,
    },
    touchInfo: {
        flex: 1,
        borderWidth: 0.5,
        width: 80,
        justifyContent: "center",
        alignItems: 'center',
        borderColor: '#24C3A3',
        backgroundColor: '#D3FFF6'
    },
    billInfoContainer: {
        flex: 1,
        flexDirection: 'row',
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        borderTopColor: 'gray',
        borderTopWidth: 0.5,
        alignItems: 'center',

    },
    orderDishContainer: {
        flex: 8,
        flexDirection: "row",
    },
    categoryList: {
        flex: 3,
        borderRightColor: 'gray',
        borderRightWidth: 0.5
    }
})
