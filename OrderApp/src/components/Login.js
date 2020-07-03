import React, { useRef, useState } from 'react'
import {
    Text, StyleSheet, View, Image,
    TouchableWithoutFeedback, StatusBar,
    TextInput, SafeAreaView, Keyboard, TouchableOpacity,
    KeyboardAvoidingView
} from 'react-native'
import { useDispatch, useSelector } from 'react-redux'
import Feather from 'react-native-vector-icons/Feather';
import { actionLogin } from '../actions/loginAction';
import { LIST_TABLE_SCREEN } from '../common/screenName';

export default function Login({ navigation }) {
    const [secure, setSecure] = useState(true)
    const [phone, setPhone] = useState('')
    const [password, setPassword] = useState('')

    const authenticated = useSelector(state => state.loginReducer.authenticated)
    const accessToken = useSelector(state => state.loginReducer.accessToken)
    // console.log(`dang nhap thanh cong voi authenticated ${authenticated} va accessToken la ${accessToken}`)
    authenticated && navigation.navigate(LIST_TABLE_SCREEN, { accessToken })


    const dispatch = useDispatch()
    function handleLogin() {
        dispatch(actionLogin({
            phone: phone,
            password: password
        }))
    }
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
                            <View style={styles.inputSide}>
                                <Feather name='phone' size={26} color='#24C3A3' />
                                <TextInput style={styles.input}
                                    onChangeText={text => setPhone(text)}
                                    placeholder="Nhập số điên thoại"
                                    keyboardType="number-pad"
                                    autoCorrect={false}
                                />
                            </View>
                            <Text style={styles.titleInput}>Mật khẩu</Text>
                            <View style={styles.inputSide}>
                                <Feather name='lock' size={26} color='#24C3A3' />
                                <TextInput style={styles.input}
                                    placeholder="Nhập password"
                                    onChangeText={text => setPassword(text)}
                                    returnKeyType="go"
                                    secureTextEntry={secure}
                                    autoCapitalize="none"
                                    autoCorrect={false}
                                />
                                <TouchableOpacity onPress={() => setSecure(!secure)}>
                                    <Feather name={secure ? 'eye' : 'eye-off'} size={26} color='gray' />
                                </TouchableOpacity>
                            </View>
                            <TouchableOpacity
                                style={styles.buttonContainer}
                                onPress={() => handleLogin()}
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
    inputSide: {
        height: 40,
        flexDirection: 'row',
        marginBottom: 30,
        borderBottomColor: 'gray',
        borderBottomWidth: 1,
        alignItems: 'center'
    },
    input: {
        flex: 1,
        color: 'black',
        fontSize: 16,
        marginLeft: 10

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
