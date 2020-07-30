import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity, Image } from 'react-native'

export default function TableFatherComponent({ section, _handleChangeStatusOrder }) {
    return (
        <View style={styles.container}>
            <TouchableOpacity
                onPress={() => _handleChangeStatusOrder(section.orderId, section.tableName, section.totalQuantity, 20)}
                style={styles.container}>
                <Text style={{ marginHorizontal: 8, fontSize: 24, fontWeight: '700', color: '#00711F' }}>
                    {section.totalQuantity}
                </Text>
                <View style={{ flexDirection: 'column', flex: 1, paddingHorizontal: 10 }}>
                    <View style={{ flexDirection: "row", marginBottom: 10 }}>
                        <Text style={{ fontSize: 18, fontWeight: '500', color: '#00711F' }}>
                            {`${section.tableName}`}
                        </Text>
                        {/* <Text style={{ fontSize: 16, marginLeft: 5 }}>
                            {`- ${section.order}`}
                        </Text> */}
                    </View>
                    <Text style={{ fontSize: 16, color: '#00711F' }}>
                        {`${section.timeOrder}`}
                    </Text>
                </View>
                {
                    section.statusId == 11 &&
                    <TouchableOpacity
                        onPress={() => _handleChangeStatusOrder(section.orderId, section.tableName, section.totalQuantity, 19)}
                    >
                        <Image style={{ width: 35, height: 35, marginHorizontal: 8, }} source={require('./../../../assets/pan.png')} />
                    </TouchableOpacity>
                }

            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        height: 60,
        backgroundColor: 'white',
        borderColor: 'rgba(0,0,0,0.5)',
        borderWidth: 0.5,
        alignItems: 'center',
        borderRadius: 5
    }
})
