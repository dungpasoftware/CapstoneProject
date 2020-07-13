import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TouchableHighlight } from 'react-native'
import { MAIN_COLOR } from '../../common/color'
import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')

function NumberButton({ number, handleClickNumber }) {
    return (
        <View style={{
            flex: 1,
            borderColor: 'black',
            borderWidth: 0.5,
            marginHorizontal: 3,
            marginBottom: 6
        }}>
            <TouchableHighlight
                onPress={(number) => handleClickNumber}
                underlayColor={MAIN_COLOR}
                style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center'
                }}>
                <Text style={{ textAlign: "center", fontSize: 16, fontWeight: '600' }}>{number}</Text>
            </TouchableHighlight>
        </View>
    )
}

function ChangeAmountAndPrice(props, ref) {

    const [amount, setAmount] = useState(0)
    const [price, setPrice] = useState(0)
    const [isAmountFocuse, setIsAmountFocuse] = useState(true)

    const changeAPRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showChangeAPRefBox: () => {
            changeAPRef.current.open();
        }
    }));
    const handleClickNumber = (number) => {
        console.log(number)
    }
    return (
        <Modal
            ref={changeAPRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
                shadowRadius: 10,
                width: screen.width - 20,
                height: 400,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <View style={styles.container}>
                <Text style={{ flex: 2 }}>Chọn vào 2 ô nhập bên dưới để thay đổi số lượng và đơn giá hiện tại</Text>
                {/* Phần tính toán */}
                <View style={{ flexDirection: 'row', flex: 2, }}>
                    <TouchableOpacity
                        style={{ flex: 1, }}>
                        <Text style={{ textAlign: 'center', backgroundColor: isAmountFocuse ? '#9FE5D7' : 'white' }}>
                            {amount}
                        </Text>
                    </TouchableOpacity>
                    <Text style={{ marginHorizontal: 3 }}>x</Text>
                    <TouchableOpacity
                        style={{ flex: 1 }}>
                        <Text style={{ textAlign: 'center', backgroundColor: isAmountFocuse ? 'white' : '#9FE5D7' }}>
                            {price}
                        </Text>
                    </TouchableOpacity>
                    <Text style={{ marginHorizontal: 3 }}>=</Text>
                    <Text style={{ flex: 1, textAlign: "center" }}>{amount * price}</Text>
                </View>
                <View style={{ flexDirection: 'row', flex: 3, }}>
                    <NumberButton number={1} handleClickNumber={handleClickNumber} />
                    <NumberButton number={2} handleClickNumber={handleClickNumber} />
                    <NumberButton number={3} handleClickNumber={handleClickNumber} />
                    <NumberButton number={4} handleClickNumber={handleClickNumber} />
                    <NumberButton number={5} handleClickNumber={handleClickNumber} />
                    <NumberButton number={7} handleClickNumber={handleClickNumber} />
                </View>
                <View style={{ flexDirection: 'row', flex: 3 }}>
                    <NumberButton number={7} handleClickNumber={handleClickNumber} />
                    <NumberButton number={8} handleClickNumber={handleClickNumber} />
                    <NumberButton number={9} handleClickNumber={handleClickNumber} />
                    <NumberButton number={0} handleClickNumber={handleClickNumber} />
                    <NumberButton number={"."} handleClickNumber={handleClickNumber} />
                    <NumberButton number={"Del"} handleClickNumber={handleClickNumber} />
                </View>
                {/* Phần button dưới cùng */}
                <View style={{ flexDirection: 'row', flex: 2 }}>
                    <TouchableOpacity>
                        <Text>Hủy</Text>
                    </TouchableOpacity>
                    <TouchableOpacity>
                        <Text>Đồng ý</Text>
                    </TouchableOpacity>
                </View>
            </View>
        </Modal >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        padding: 8
    }
})

export default ChangeAmountAndPrice = forwardRef(ChangeAmountAndPrice);
