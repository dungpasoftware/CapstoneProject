import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity, Image } from 'react-native'

export default function TableFatherComponent({ section }) {
    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.container}>
                <Text style={{ marginHorizontal: 8, fontSize: 24, fontWeight: '700' }}>
                    {section.amount}
                </Text>
                <View style={{ flexDirection: 'column', flex: 1, paddingHorizontal: 10 }}>
                    <View style={{ flexDirection: "row", marginBottom: 10 }}>
                        <Text style={{ fontSize: 18, fontWeight: '500' }}>
                            {`${section.table}-${section.location}`}
                        </Text>
                        <Text style={{ fontSize: 16, marginLeft: 5 }}>
                            {`- ${section.order}`}
                        </Text>
                    </View>
                    <Text style={{ fontSize: 16 }}>
                        {`${section.timeStart} - (${section.timeCount})`}
                    </Text>
                </View>
                <TouchableOpacity>
                    <Image style={{ width: 35, height: 35, marginHorizontal: 8, }} source={require('./../../../assets/pan.png')} />
                </TouchableOpacity>

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
        alignItems: 'center'
    }
})
