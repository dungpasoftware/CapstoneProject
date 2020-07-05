import React from 'react'
import { StyleSheet, Text, View, SectionList, SafeAreaView } from 'react-native'
import dataKichen from '../dataKitchen'
import TableFatherComponent from './TableFatherComponent'
import DishChildComponent from './DishChildComponent'

export default function KitchenByTable() {
    return (
        <View style={styles.container}>
            <SectionList
                sections={dataKichen}
                renderSectionHeader={({ section }) => <TableFatherComponent section={section} />}
                renderItem={({ item, index }) => <DishChildComponent item={item} index={index} />}
                keyExtractor={(item, index) => item.id.toString()}
                renderSectionFooter={() => <View style={{ height: 10, flex: 1 }}></View>}
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
