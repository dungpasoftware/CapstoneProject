import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import {
    View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity,
    TextInput, TouchableWithoutFeedback, Keyboard, FlatList
} from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

import Modal from 'react-native-modalbox'

var screen = Dimensions.get('window')

function ToppingItem({ item }) {
    return (
        <View style={{
            flex: 1,
            height: 45,
            borderBottomColor: 'black',
            borderBottomWidth: 0.5
        }}>
            <TouchableOpacity
                style={{
                    flex: 1,
                    flexDirection: 'row',
                    alignItems: 'center'
                }}
            >
                <Text style={{ color: 'black', flex: 1, fontSize: 16 }}>
                    {item.optionType == "MONEY" ?
                        item.optionName + ` (${new Intl.NumberFormat().format(item.price)} đ)` :
                        item.optionName}
                </Text>
                <Text style={{ fontSize: 22, marginHorizontal: 8 }}>1</Text>
                <TouchableOpacity>
                    <Feather name="minus" size={30} />
                </TouchableOpacity>
            </TouchableOpacity>

        </View>
    )
}

function ToppingBox(props, ref) {

    const [dishItem, setDishItem] = useState({})

    const toppingBoxRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showToppingBox: (item) => {
            setDishItem(item)
            toppingBoxRef.current.open();
        }
    }));
    console.log(dishItem)
    return (
        <Modal
            ref={toppingBoxRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
                shadowRadius: 10,
                width: screen.width - 60,
                height: 400,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <TouchableWithoutFeedback style={styles.container} onPress={Keyboard.dismiss}>
                <View style={styles.container}>
                    <View style={styles.titleBar}>
                        <Text style={{ color: 'white', fontWeight: '700', fontSize: 18 }}>{dishItem.dishName}</Text>
                        <TouchableOpacity
                            onPress={() => { toppingBoxRef.current.close() }}
                        >
                            <Feather name="x" size={30} color='white' />
                        </TouchableOpacity>
                    </View>


                    <View style={styles.content}>
                        <View style={{ flexDirection: 'row' }}>
                            <TextInput
                                style={{
                                    flex: 1,
                                    height: 40,
                                    color: 'black',
                                    marginBottom: 10,
                                    fontSize: 16,
                                    borderBottomColor: 'gray',
                                    borderBottomWidth: 1,
                                }}
                            />
                            <TouchableOpacity style={{ marginTop: 10, marginLeft: 5 }}>
                                <Feather name="x" size={30} />
                            </TouchableOpacity>
                        </View>
                        <FlatList
                            data={dishItem.options}
                            style={{ flex: 1 }}
                            keyExtractor={(item, index) => item.optionId.toString()}
                            renderItem={({ item, index }) => <ToppingItem item={item} />}
                        />
                    </View>


                    <View style={styles.buttonBar}>
                        <View style={{ flex: 1, flexDirection: 'row' }}>
                            <TouchableOpacity style={[styles.button, { width: 70 }]}>
                                <Text style={styles.textButton}>,</Text>
                            </TouchableOpacity>
                            <TouchableOpacity style={[styles.button, { width: 70, marginLeft: 10 }]}>
                                <Text style={styles.textButton}>Enter</Text>
                            </TouchableOpacity>
                        </View>
                        <View style={{ flex: 1, alignItems: 'flex-end' }}>
                            <TouchableOpacity
                                onPress={() => { toppingBoxRef.current.close() }}
                                style={[styles.button, { width: 100 }]}>
                                <Text style={styles.textButton}>Đồng ý</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>
            </TouchableWithoutFeedback>

        </Modal>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    },
    titleBar: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: '#24C3A3',
        alignItems: "center",
        justifyContent: 'space-between',
        paddingHorizontal: 20,
    },
    content: {
        flex: 7,
        flexDirection: 'column',
        marginHorizontal: 20,
    },
    buttonBar: {
        flex: 1,
        flexDirection: 'row',
        paddingHorizontal: 20,
        marginVertical: 10,

    },
    button: {
        height: 40,
        backgroundColor: '#24C3A3',
        justifyContent: 'center',
        alignItems: "center",
    },
    textButton: {
        color: 'white',
        fontSize: 18,
        fontWeight: '600',
        textAlign: "center"
    }
})

export default ToppingBox = forwardRef(ToppingBox);
