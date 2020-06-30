import React from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'
import dataDisher from '../dataDisher'
import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'

export default function OrderedScreen() {
    return (
        <View style={styles.container}>
            <View style={{ flex: 9 }}>
                <FlatList
                    data={dataDisher}
                    renderItem={({ item, index }) => {
                        return (
                            <Ordered2Item item={item} index={index} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Thanh toán" />

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
