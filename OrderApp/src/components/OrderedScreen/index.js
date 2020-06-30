import React, { useRef } from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'
import dataDisher from '../dataDisher'
import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'

export default function OrderedScreen() {
    const optionDishRef = useRef(null);
    function showOptionDish() {
        optionDishRef.current.showOptionDishBox();
    }
    return (
        <View style={styles.container}>
            <View style={{ flex: 9 }}>
                <FlatList
                    data={dataDisher}
                    renderItem={({ item, index }) => {
                        return (
                            <Ordered2Item item={item} index={index} showOptionDish={showOptionDish} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Thanh toán" />
            <OptionDishOrdered ref={optionDishRef} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'white'

    }
})
