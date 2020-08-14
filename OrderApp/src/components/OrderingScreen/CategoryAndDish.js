import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { StyleSheet, Text, View, FlatList } from 'react-native'

import CategoryItem from './CategoryItem'
import DishItem from './DishItem'

export default function CategoryAndDish({ showToppingBox, accessToken, showDescriptionBox }) {
    const [listDishFilter, setListDishFilter] = useState([])
    const [categoryId, setCategoryId] = useState(0)

    const { listDish, listCategory } = useSelector(state => state.listDish)



    useEffect(() => {
        async function _retrieveTableData() {
            let newListDish = [...listDish]
            if (categoryId != 0) {
                newListDish = await newListDish.filter((dish) => {
                    let haveDish = false
                    dish.categories.forEach(category => {
                        if (category.categoryId == categoryId) {
                            haveDish = true
                        }
                    });
                    return haveDish
                })
            }
            await setListDishFilter(newListDish)
        };
        _retrieveTableData()
    }, [categoryId, listDish])
    return (
        <View style={styles.container}>
            <View style={styles.categoryList}>
                <FlatList
                    data={listCategory}
                    keyExtractor={(item, index) => item.categoryId.toString()}
                    renderItem={({ item, index }) => {
                        return (
                            <CategoryItem item={item} index={index} categoryId={categoryId} setCategoryId={setCategoryId} />
                        )
                    }}
                />
            </View>
            <View style={{ flex: 9 }}>
                <FlatList
                    data={listDishFilter}
                    keyExtractor={(item, index) => item.dishId.toString()}
                    renderItem={({ item, index }) => {
                        return (
                            <DishItem item={item} index={index} showToppingBox={showToppingBox} showDescriptionBox={showDescriptionBox} />
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
