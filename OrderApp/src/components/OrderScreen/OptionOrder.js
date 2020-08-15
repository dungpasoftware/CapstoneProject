import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity } from 'react-native'
import Modal from 'react-native-modalbox'

var screen = Dimensions.get('window')

function OptionButton({ text, color, handle, index }) {
    return (
        <View
            style={{
                flex: 1,
                borderBottomColor: 'gray',
                borderBottomWidth: 0.5
            }}>
            <TouchableOpacity
                onPress={() => handle(index)}
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

function OptionOrder({ selectOptionMenu, statusOrder }, ref) {
    const optionOrderRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showOptionOrderBox: () => {
            optionOrderRef.current.open();
        },
        closeOptionOrderBox: () => {
            optionOrderRef.current.close();
        }
    }));


    function _handleClickMenuOrder(index) {
        selectOptionMenu(index)
        optionOrderRef.current.close()
    }
    const checkStatus = () => {
        switch (statusOrder) {
            case 10:
                return "Đang bận"
            case 11:
                return "Đã order"
            case 12:
                return "Bếp đang làm"
            case 13:
                return "Đã hoàn thành"
            case 14:
                return "Chờ thanh toán"
            case 15:
                return "Xác nhận thanh toán"
            default: return ""
        }
    }
    let isWaitting = statusOrder == 10
    let newHeight = isWaitting ? 290 : 370
    if (statusOrder == 14) newHeight = 130
    if (statusOrder == 15) newHeight = 130
    return (
        <Modal
            ref={optionOrderRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
                shadowRadius: 10,
                width: screen.width / 1.5,
                height: newHeight,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}

        >
            <View style={styles.container}>
                <View style={{ flex: 1, backgroundColor: '#24C3A3', justifyContent: 'space-evenly', alignItems: "center" }}>
                    <Text
                        style={{
                            textAlign: "center",
                            color: 'white', fontSize: 20,
                            fontWeight: 'bold',
                            textAlign: "center"
                        }}>
                        Trạng thái:
                    </Text>
                    <Text
                        style={{
                            textAlign: "center",
                            color: 'white', fontSize: 20,
                            fontWeight: 'bold',
                            textAlign: "center"
                        }}>
                        {`${checkStatus()}`}
                    </Text>
                </View>
                <OptionButton text='Ghi chú cho bàn ăn' color='black' handle={_handleClickMenuOrder} index={1} />
                {!(statusOrder == 14 || statusOrder == 15) && <OptionButton text='Chuyển bàn' color='black' handle={_handleClickMenuOrder} index={2} />}
                {!(isWaitting || statusOrder == 14 || statusOrder == 15) && <OptionButton text='Trả Món' color='black' handle={_handleClickMenuOrder} index={3} />}
                {!(statusOrder == 14 || statusOrder == 15) && <OptionButton text='Hủy bàn ăn' color='red' handle={_handleClickMenuOrder} index={4} />}
            </View>
        </Modal>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    }
})

export default OptionOrder = forwardRef(OptionOrder);
