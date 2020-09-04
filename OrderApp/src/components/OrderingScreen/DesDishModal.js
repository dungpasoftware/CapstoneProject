import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';


import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')


function DesDishModal(props, ref) {
    const [description, setDescription] = useState({
        dishName: '',
        moTa: '',
        thanhPhan: '',
        topping: ''
    })


    const descriptionDishRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showDescriptionDishBox: (item) => {
            descriptionDishRef.current.open()
            let newThanhPhan = item.quantifiers.reduce((acc, curr, index) => {
                let reduceThanhPhan = index < item.quantifiers.length - 1 ?
                    acc.concat(curr.material.materialName).concat(', ') : acc.concat(curr.material.materialName).concat('.')
                return reduceThanhPhan
            }, '')
            let newTopping = item.options.reduce((acc, curr, index) => {
                let reduceTopping = index < item.options.length - 1 ?
                    acc.concat(curr.optionName).concat(', ') : acc.concat(curr.optionName).concat('.')
                return reduceTopping
            }, '')
            setDescription({
                dishName: item.dishName,
                moTa: item.description != null ? item.description : 'Món này không có mô tả!',
                thanhPhan: newThanhPhan,
                topping: newTopping

            })
        }
    }));




    return (
        <Modal
            ref={descriptionDishRef}
            style={{
                borderRadius: 15,
                shadowRadius: 10,
                width: screen.width - 50,
                height: 400,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <View style={styles.container}>
                <View style={{
                    flex: 1,
                    flexDirection: 'row',
                    backgroundColor: '#24C3A3',
                    alignItems: "center",
                    justifyContent: 'space-between',
                    paddingHorizontal: 20,
                }}>
                    <Text style={{ color: 'white', fontWeight: '700', fontSize: 18 }}>
                        {description.dishName}
                    </Text>
                    <TouchableOpacity
                        onPress={() => { descriptionDishRef.current.close() }}
                    >
                        <Feather name="x" size={30} color='white' />
                    </TouchableOpacity>
                </View>
                <View style={{ flex: 6, paddingVertical: 10, paddingHorizontal: 5 }}>

                    <View style={{ flex: 1, flexDirection: 'column', marginBottom: 3 }}>
                        <Text
                            style={{
                                fontSize: 17,
                                fontWeight: '600'
                            }}
                        >
                            - Mô tả:</Text>
                        <Text style={{ marginLeft: 10, marginTop: 3, fontSize: 15 }}>{description.moTa}</Text>
                    </View>

                    <View style={{ flex: 1, flexDirection: 'column', marginBottom: 3 }}>
                        <Text style={{
                            fontSize: 17,
                            fontWeight: '600'
                        }}>- Thành phần:</Text>
                        <Text style={{ marginLeft: 10, marginTop: 3, fontSize: 15 }}>{description.thanhPhan}</Text>
                    </View>

                    <View style={{ flex: 1, flexDirection: 'column', marginBottom: 3 }}>
                        <Text style={{
                            fontSize: 17,
                            fontWeight: '600'
                        }}>- Topping:</Text>
                        <Text style={{ marginLeft: 10, marginTop: 3, fontSize: 15 }}>{description.topping}</Text>
                    </View>

                </View>
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

export default DesDishModal = forwardRef(DesDishModal);
