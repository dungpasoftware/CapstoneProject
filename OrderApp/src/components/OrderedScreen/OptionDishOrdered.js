import React, { forwardRef, useRef, useImperativeHandle } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity } from 'react-native'
import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')

function OptionButton({ text, color }) {
    return (
        <View style={{
            flex: 1,
            borderBottomColor: 'gray',
            borderBottomWidth: 0.5
        }}>
            <TouchableOpacity style={{
                flex: 1,
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <Text style={{ textAlign: "center", color, fontSize: 16, fontWeight: '600' }}>{text}</Text>
            </TouchableOpacity>
        </View>
    )
}

function OptionDishOrdered(props, ref) {
    const optionDishRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showOptionDishBox: () => {
            optionDishRef.current.open();
        }
    }));

    return (
        <Modal
            ref={optionDishRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
                shadowRadius: 10,
                width: screen.width / 2,
                height: 400,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <View style={styles.container}>
                <View style={{ flex: 1, backgroundColor: '#24C3A3', justifyContent: "center", alignItems: "center" }}>
                    <Text style={{ textAlign: "center", color: 'white', fontSize: 20, fontWeight: 'bold', textAlign: "center" }}>Mon A</Text>
                </View>
                <OptionButton text='Thay s.lượng & giá' color='black' />
                <OptionButton text='Topping, ghi chú' color='black' />
                <OptionButton text='Hủy món' color='black' />
                <OptionButton text='Giảm giá' color='black' />
            </View>
        </Modal >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    }
})

export default OptionDishOrdered = forwardRef(OptionDishOrdered);
