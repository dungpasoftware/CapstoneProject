import React, { forwardRef, useRef, useImperativeHandle } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity } from 'react-native'
import Modal from 'react-native-modalbox'
import { RETURN_DISH_SCREEN } from '../../common/screenName'

var screen = Dimensions.get('window')

function OptionButton({ text, color, handle }) {
    return (
        <View
            style={{
                flex: 1,
                borderBottomColor: 'gray',
                borderBottomWidth: 0.5
            }}>
            <TouchableOpacity
                onPress={() => handle()}
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

function OptionOrder({ navigation }, ref) {
    const optionOrderRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showOptionOrderBox: () => {
            optionOrderRef.current.open();
        },
        closeOptionOrderBox: () => {
            optionOrderRef.current.close();
        }
    }));

    function handleReturnDish() {
        optionOrderRef.current.close();
        navigation.navigate(RETURN_DISH_SCREEN)
    }
    return (
        <Modal
            ref={optionOrderRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
                shadowRadius: 10,
                width: screen.width / 2,
                height: 300,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <View style={styles.container}>
                <OptionButton text='Ghi chú cho bàn ăn' color='black' handle={handleReturnDish} />
                <OptionButton text='Chuyển bàn' color='black' handle={handleReturnDish} />
                <OptionButton text='Trả Món' color='black' handle={handleReturnDish} />
                <OptionButton text='Báo thanh toán' color='black' handle={handleReturnDish} />
                <OptionButton text='Hủy bàn ăn' color='red' handle={handleReturnDish} />
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
