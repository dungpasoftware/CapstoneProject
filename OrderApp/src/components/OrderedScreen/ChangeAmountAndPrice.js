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
                onPress={() => handleClickNumber(number)}
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

function ChangeAmountAndPrice({ saveDataChangeAP }, ref) {
    const [newItemSelected, setNewItemSelected] = useState({})
    const [amount, setAmount] = useState('0')
    const [price, setPrice] = useState('0')
    const [isAmountFocuse, setIsAmountFocuse] = useState(true)

    const changeAPRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showChangeAPRefBox: (itemSelected) => {
            setNewItemSelected(itemSelected)
            setAmount(itemSelected.quantity.toString())
            setPrice(itemSelected.sellPrice.toString())
            changeAPRef.current.open();
        }
    }));
    const handleSubmitChange = () => {
        let newData = {
            orderOrderId: newItemSelected.orderOrderId,
            orderDishId: newItemSelected.orderDishId,
            quantity: parseInt(amount),
            sellPrice: parseFloat(price),
            sumPrice: parseFloat(amount) * parseFloat(price)
        }
        saveDataChangeAP(newData)
        changeAPRef.current.close()

    }

    function handleClickNumber(number) {
        const fakeSet = isAmountFocuse ? setAmount : setPrice
        let newResult = isAmountFocuse ? amount : price

        if (number == "Del") {

            if (newResult.length <= 1) {
                newResult = "0"
            } else {
                newResult = newResult.slice(0, -1);
            }

        } else if (number == "AC") {
            newResult = "0"
        }
        else {
            if (newResult.length < 10) {
                if (newResult == "0") {
                    newResult = number
                } else {
                    newResult += number
                }
            }

        }
        fakeSet(newResult)
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
                <View style={{ flexDirection: 'row', flex: 3, alignItems: 'center' }}>
                    <TouchableOpacity
                        onPress={() => setIsAmountFocuse(true)}
                        style={{ flex: 1, }}>
                        <Text style={{
                            textAlign: 'center', backgroundColor: isAmountFocuse ? '#9FE5D7' : 'white'
                            , fontSize: 18, fontWeight: '600'
                        }}>
                            {amount}
                        </Text>
                    </TouchableOpacity>
                    <Text style={{ marginHorizontal: 3 }}>x</Text>
                    <TouchableOpacity
                        onPress={() => setIsAmountFocuse(false)}
                        style={{ flex: 1 }}>
                        <Text style={{
                            textAlign: 'center', backgroundColor: isAmountFocuse ? 'white' : '#9FE5D7'
                            , fontSize: 18, fontWeight: '600'
                        }}>
                            {new Intl.NumberFormat().format(price)}
                        </Text>
                    </TouchableOpacity>
                    <Text style={{ marginHorizontal: 3 }}>=</Text>
                    <Text style={{
                        flex: 1, textAlign: "center", fontSize: 18, fontWeight: '600'
                    }}>{parseFloat(amount) * parseFloat(price)}</Text>
                </View>
                <View style={{ flexDirection: 'row', flex: 3, }}>
                    <NumberButton number={'1'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'2'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'3'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'4'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'5'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'7'} handleClickNumber={handleClickNumber} />
                </View>
                <View style={{ flexDirection: 'row', flex: 3 }}>
                    <NumberButton number={'7'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'8'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'9'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={'0'} handleClickNumber={handleClickNumber} />
                    <NumberButton number={"Del"} handleClickNumber={handleClickNumber} />
                    <NumberButton number={"AC"} handleClickNumber={handleClickNumber} />
                </View>
                {/* Phần button dưới cùng */}
                <View style={{ flexDirection: 'row', flex: 2, justifyContent: 'space-evenly', alignItems: 'center' }}>
                    <TouchableOpacity
                        onPress={() => changeAPRef.current.close()}
                        style={{
                            backgroundColor: 'red', flex: 1, height: 40, alignItems: "center",
                            justifyContent: 'center', marginHorizontal: 5
                        }}>
                        <Text
                            style={{ textAlign: 'center', color: 'white', fontSize: 18, fontWeight: 'bold' }}
                        >Hủy
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        onPress={handleSubmitChange}
                        style={{
                            backgroundColor: 'green', flex: 1, height: 40, alignItems: "center",
                            justifyContent: 'center', marginHorizontal: 5
                        }}>
                        <Text
                            style={{
                                textAlign: 'center', color: 'white',
                                fontSize: 18, fontWeight: 'bold'
                            }}
                        >Đồng ý
                            </Text>
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
