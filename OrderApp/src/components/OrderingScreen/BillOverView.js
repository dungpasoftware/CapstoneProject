import React from 'react'
import 'intl';
import 'intl/locale-data/jsonp/en'; // or any other locale you need
import { StyleSheet, Text, View, Image, TouchableOpacity, ActivityIndicator } from 'react-native'
import { MAIN_COLOR } from '../../common/color';

export default function BillOverview({ buttonName, totalAmount, totalItem, handle, isLoading, isDisable }) {
    return (
        <View
            pointerEvents={isDisable}
            style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ alignSelf: 'center', flex: 2 }} size="large" color={MAIN_COLOR} />
                : <View style={{ flex: 1, flexDirection: 'row', }}>
                    <View style={{
                        flex: 1,
                        flexDirection: "row",
                        alignItems: "center",
                        justifyContent: isDisable ? 'center' : 'flex-start',
                    }}>
                        <Image style={{ height: 30, width: 30, marginHorizontal: 8 }} source={require('./../../assets/contract.png')} />
                        <Text style={{ fontWeight: '600', fontSize: 16 }}>{totalItem != null ? totalItem : 0}</Text>
                    </View>

                    <View
                        style={{
                            flex: isDisable ? 1 : 4,
                            justifyContent: 'flex-start',
                            flexDirection: "row",
                            alignItems: "center",
                            marginLeft: 20,
                        }} >
                        <Image style={{ height: 30, width: 30, marginHorizontal: 8 }} source={require('./../../assets/dollar.png')} />
                        <Text style={{ color: 'red', fontWeight: '600', fontSize: 16 }}>{`${new Intl.NumberFormat().format(totalAmount)} đ`}</Text>
                    </View>
                    {!isDisable &&
                        <TouchableOpacity
                            onPress={handle}
                            style={[styles.touchInfo, { backgroundColor: buttonName == "Hủy Thanh Toán" ? '#ffabab' : '#D3FFF6' }]}>
                            <Text style={{ fontWeight: 'bold', fontSize: 18, textAlign: 'center' }}>{buttonName}</Text>
                        </TouchableOpacity>
                    }
                </View>
            }
        </View>

    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        borderTopColor: 'gray',
        borderTopWidth: 0.5,
    },
    touchInfo: {
        flex: 2,
        borderWidth: 0.5,
        width: 80,
        justifyContent: "center",
        alignItems: 'center',
        borderColor: '#24C3A3',

    },
})
