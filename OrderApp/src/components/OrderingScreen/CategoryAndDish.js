import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { StyleSheet, Text, View, FlatList } from 'react-native'

import { loadDish } from './../../actions/listDish'
import CategoryItem from './CategoryItem'
import DishItem from './DishItem'
import dishApi from '../../api/dishApi';

export default function CategoryAndDish({ showToppingBox, accessToken, showDescriptionBox }) {
    const dispatch = useDispatch()
    const [categories, setCategories] = useState([])
    const [categoryId, setCategoryId] = useState(1)

    const { listDish, isLoading } = useSelector(state => state.listDish)

    useEffect(() => {
        async function _loadCategoryData() {
            const listCategoryAPI = await dishApi.listAllCategory(accessToken)
            let newListCategory = [...listCategoryAPI]
            newListCategory.unshift({
                categoryId: 0,
                categoryName: 'Tất cả',
            })
            await setCategories(newListCategory)
            await setCategoryId(newListCategory[0].categoryId)
        };
        _loadCategoryData()
    }, [])


    useEffect(() => {
        async function _retrieveTableData() {
            await dispatch(loadDish({ categoryId, accessToken }))
        };
        _retrieveTableData()
    }, [categoryId])
    return (
        <View style={styles.container}>
            <View style={styles.categoryList}>
                <FlatList
                    data={categories}
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
                    data={listDish}
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
