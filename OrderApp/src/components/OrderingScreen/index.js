import React, { useRef } from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import dataDisher from '../dataDisher'
import dataCategory from '../dataCategory'
import OrderedItem from './OrderedItem'
import CategoryItem from './CategoryItem'
import DishItem from './DishItem'
import BillOverview from './BillOverView'
import ToppingBox from '../OrderScreen/ToppingBox'



export default function OrderingScreen() {
    const toppingBoxRef = useRef(null)
    function showToppingBox() {
        toppingBoxRef.current.showToppingBox();
    }
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
            <BillOverview buttonName="Lưu" />
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
                                <DishItem item={item} index={index} showToppingBox={showToppingBox} />
                            )
                        }}
                    />
                </View>
            </View>
            <ToppingBox ref={toppingBoxRef} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'white'
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
