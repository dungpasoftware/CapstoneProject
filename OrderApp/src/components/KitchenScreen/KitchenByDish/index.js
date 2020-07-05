import React from 'react'
import { StyleSheet, View, SectionList } from 'react-native'
import dataKichen from '../dataKitchen'
import TableChildComponent from './TableChildComponent'
import DishFatherComponent from './DishFatherComponent'

export default function KitchenByDish() {
    return (
        <View style={styles.container}>
            <SectionList
                sections={dataKichen}
                renderSectionHeader={({ section }) => <DishFatherComponent section={section} />}
                renderItem={({ item, index }) => <TableChildComponent item={item} index={index} />}
                renderSectionFooter={() => <View style={{ height: 10, flex: 1 }}></View>}
                keyExtractor={(item, index) => item.id.toString()}
            />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        paddingHorizontal: 8,
        backgroundColor: '#D9D9D9',
        paddingVertical: 10

    }
})
