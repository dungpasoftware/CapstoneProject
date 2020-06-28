import React, { useRef } from 'react'
import {
    Text, StyleSheet, View, Image,
    TouchableWithoutFeedback, StatusBar,
    TextInput, SafeAreaView, Keyboard, TouchableOpacity,
    KeyboardAvoidingView
} from 'react-native'

export default function Login({ navigation }) {
    const passwordRef = useRef(null);
    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle='light-content' />
            <KeyboardAvoidingView behavior='padding' style={styles.container}>
                <TouchableWithoutFeedback style={styles.container} onPress={Keyboard.dismiss}>
                    <View style={styles.container}>
                        <View style={styles.logoContainer}>
                            <Image style={styles.logo}
                                source={require('../assets/logo.png')}
                            />
                            <Text style={styles.title}>Order App</Text>
                        </View>
                        <View style={styles.infoContainer}>
                            <Text style={styles.titleInput}>Số điện thoại</Text>
                            <TextInput style={styles.input}
                                placeholder="Nhập số điên thoại"
                                keyboardType="number-pad"
                                returnKeyType="next"
                                autoCorrect={false}
                                onSubmitEditing={() => passwordRef.current.focus()}

                            />
                            <Text style={styles.titleInput}>Mật khẩu</Text>
                            <TextInput style={styles.input}
                                placeholder="Nhập password"
                                returnKeyType="go"
                                secureTextEntry
                                autoCapitalize="none"
                                autoCorrect={false}
                                ref={passwordRef}
                            />
                            <TouchableOpacity
                                style={styles.buttonContainer}
                                onPress={() => navigation.navigate('ListTable')}
                            >
                                <Text style={styles.buttonText}>Đăng nhập</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </TouchableWithoutFeedback>
            </KeyboardAvoidingView >
        </SafeAreaView >
    )

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'white',
        flexDirection: 'column'
    },
    logoContainer: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
    },
    logo: {
        width: 128,
        height: 56,
    },
    title: {
        color: '#24C3A3',
        fontSize: 28,
        fontWeight: 'bold',
        textAlign: "center",
        marginTop: 5,
    },
    infoContainer: {
        flex: 2,
        padding: 20,
    },
    input: {
        height: 40,
        color: 'black',
        marginBottom: 30,
        fontSize: 16,
        borderBottomColor: 'gray',
        borderBottomWidth: 1,
    },
    titleInput: {
        fontSize: 18,
        color: '#24C3A3',
        marginBottom: 8,
    },
    buttonContainer: {
        backgroundColor: '#24C3A3',
        marginTop: 15,
        paddingVertical: 15,
        borderRadius: 10

    },
    buttonText: {
        textAlign: 'center',
        color: 'white',
        fontWeight: 'bold',
        fontSize: 22
    }

})
