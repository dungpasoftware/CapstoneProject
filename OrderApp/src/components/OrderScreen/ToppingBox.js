import React, { forwardRef, useRef, useImperativeHandle } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TextInput, TouchableWithoutFeedback, Keyboard } from 'react-native'
import Modal from 'react-native-modalbox'
import Feather from 'react-native-vector-icons/Feather';

var screen = Dimensions.get('window')

function ToppingBox(props, ref) {
    const toppingBoxRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showToppingBox: () => {
            toppingBoxRef.current.open();
        }
    }));

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
                        <Text style={{ color: 'white', fontWeight: '700', fontSize: 18 }}>Mon A</Text>
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
                        <View style={{ flex: 1 }}>
                            <View style={styles.toppingCard}>
                                <Text>Kem dau 500 dong</Text>
                            </View>
                            <View style={styles.toppingCard}>
                                <Text>Kem dau 500 dong</Text>
                            </View>
                            <View style={styles.toppingCard}>
                                <Text>Kem dau 500 dong</Text>
                            </View>
                        </View>
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
    },
    toppingCard: {
        height: 40,
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        justifyContent: 'center',
        paddingHorizontal: 10,
        marginBottom: 5
    }
})

export default ToppingBox = forwardRef(ToppingBox);
