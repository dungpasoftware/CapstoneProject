import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity } from 'react-native'
import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')

function OptionButton({ text, color, option, handleMenu }) {
    return (
        <View style={{
            flex: 1,
            borderBottomColor: 'gray',
            borderBottomWidth: 0.5
        }}>
            <TouchableOpacity
                onPress={() => handleMenu(option)}
                style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center'
                }}>
                <Text style={{ textAlign: "center", color, fontSize: 16, fontWeight: '600' }}>{text}</Text>
            </TouchableOpacity>
        </View>
    )
}

function TableOption({ handleMenu }, ref) {

    const [itemSelected, setItemSelected] = useState({})
    const tableOptionRef = useRef(null);

    useImperativeHandle(ref, () => ({
        showTableOptionBox: (item) => {
            setItemSelected(item)
            tableOptionRef.current.open();
        }
    }));

    function handleMenuClick(option) {
        handleMenu(option, itemSelected)
        tableOptionRef.current.close()
    }
    let newHeight = itemSelected.statusId == 5 ? 280 : 400

    return (
        <Modal
            ref={tableOptionRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
                shadowRadius: 10,
                width: screen.width - screen.width / 3,
                height: newHeight,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <View style={styles.container}>
                <View style={{ flex: 1, backgroundColor: '#24C3A3', justifyContent: "center", alignItems: "center" }}>
                    <Text style={{ textAlign: "center", color: 'white', fontSize: 20, fontWeight: 'bold', textAlign: "center" }}>
                        {itemSelected.tableName}</Text>
                </View>
                {itemSelected.statusId == 6 && <OptionButton text='Báo thanh toán' color='black' option={1} handleMenu={handleMenuClick} />}
                <OptionButton text='Ghi chú' color='black' option={2} handleMenu={handleMenuClick} />
                {itemSelected.statusId == 6 && <OptionButton text='Trả món' color='black' option={3} handleMenu={handleMenuClick} />}
                <OptionButton text='Hủy bàn' color='red' option={4} handleMenu={handleMenuClick} />
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

export default TableOption = forwardRef(TableOption);
