import React from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'

import dataDisher from '../dataDisher'
import dataCategory from '../dataCategory'
import CategoryItem from './CategoryItem'
import DishItem from './DishItem'

export default function CategoryAndDish({ showToppingBox }) {
    return (
        <View style={styles.container}>
            <View style={styles.categoryList}>
                <FlatList
                    data={dataCategory}
                    keyExtractor={(item, index) => item.id}
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
                    keyExtractor={(item, index) => item.id}
                    renderItem={({ item, index }) => {
                        return (
                            <DishItem item={item} index={index} showToppingBox={showToppingBox} />
                        )
                    }}
                />
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    touchInfo: {
        flex: 1,
        borderWidth: 0.5,
        width: 80,
        justifyContent: "center",
        alignItems: 'center',
        borderColor: '#24C3A3',
        backgroundColor: '#D3FFF6'
    },

    container: {
        flex: 4,
        flexDirection: "row",
    },
    categoryList: {
        flex: 3,
        borderRightColor: 'gray',
        borderRightWidth: 0.5
    }
})
