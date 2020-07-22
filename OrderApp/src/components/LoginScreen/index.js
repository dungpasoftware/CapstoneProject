import React, { useState, useEffect } from 'react'
import {
    Text, StyleSheet, View, Image,
    TouchableWithoutFeedback, StatusBar,
    TextInput, SafeAreaView, Keyboard, TouchableOpacity,
    ActivityIndicator
} from 'react-native'
import { useDispatch, useSelector } from 'react-redux'
import Feather from 'react-native-vector-icons/Feather';
import { actionLogin } from '../../actions/loginAction';
import { LIST_TABLE_SCREEN, KITCHEN_SCREEN } from '../../common/screenName';
import { ROLE_ORDER_TAKER, ROLE_CHEF } from '../../common/roleType';
import { MAIN_COLOR } from '../../common/color';

export default function LoginScreen({ navigation }) {
    const [secure, setSecure] = useState(true)
    const [phone, setPhone] = useState('')
    const [password, setPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState('')
    const [haveError, setHaveError] = useState(false)


    const userInfo = useSelector(state => state.loginReducer.userInfo)
    const isLoading = useSelector(state => state.loginReducer.isLoading)
    const status = useSelector(state => state.loginReducer.status)

    // console.log(`dang nhap thanh cong voi authenticated ${authenticated} va accessToken la ${accessToken}`)

    useEffect(() => {
        async function _checkStatusHandle() {
            if (status != 0) {
                switch (status) {
                    case 200: {
                        switch (userInfo.role) {
                            case ROLE_ORDER_TAKER: navigation.navigate(LIST_TABLE_SCREEN, { userInfo })
                                break;
                            case ROLE_CHEF: navigation.navigate(KITCHEN_SCREEN, { userInfo })
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                    case 401: {
                        setErrorMessage('Sai tên tài khoản hoặc mật khẩu')
                        break
                    }
                    case 500: {
                        setErrorMessage('Lỗi hệ thống, xin vui lòng thử lại sau')
                        break
                    }
                    default:
                        break;
                }
            }
        }
        _checkStatusHandle()
    }, [status])


    const dispatch = useDispatch()
    function handleLogin() {
        if (phone.trim() == "" || password.trim() == "") {
            setHaveError(true)
            setErrorMessage('Bạn chưa điền đủ thông tin để đăng nhập')
            return
        }
        // var checkPhone = /^\d+$/;
        // if (!checkPhone.test(phone)) {
        //     setHaveError(true)
        //     setErrorMessage('Số điện thoại chỉ toàn là số')
        //     return
        // }
        setHaveError(false)
        dispatch(actionLogin({
            phone: phone,
            password: password
        }))
    }
    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle='light-content' />
            <TouchableWithoutFeedback style={styles.container} onPress={Keyboard.dismiss}>
                <View style={styles.container}>
                    <View style={styles.logoContainer}>
                        <Image style={styles.logo}
                            source={require('../../assets/logo.png')}
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
                                maxLength={15}
                            />
                        </View>
                        <Text style={styles.titleInput}>Mật khẩu</Text>
                        <View style={styles.inputSide}>
                            <Feather name='lock' size={26} color='#24C3A3' />
                            <TextInput style={styles.input}
                                placeholder="Nhập password"
                                onChangeText={text => setPassword(text)}
                                returnKeyType="go"
                                maxLength={15}
                                secureTextEntry={secure}
                                autoCapitalize="none"
                                autoCorrect={false}
                            />
                            <TouchableOpacity onPress={() => setSecure(!secure)}>
                                <Feather name={secure ? 'eye' : 'eye-off'} size={26} color='gray' />
                            </TouchableOpacity>
                        </View>
                        {status != 0 || haveError && <View style={{ flexDirection: 'row' }}>
                            <Feather name={'alert-triangle'} size={18} color='red' />
                            <Text style={{ color: 'red', fontSize: 16, marginLeft: 8 }}>{errorMessage}</Text>
                        </View>}


                        {isLoading ? <ActivityIndicator style={{ marginTop: 15 }} size="large" color={MAIN_COLOR} />
                            : <TouchableOpacity
                                style={styles.buttonContainer}
                                onPress={() => handleLogin()}
                            >
                                <Text style={styles.buttonText}>Đăng nhập</Text>
                            </TouchableOpacity>
                        }
                    </View>
                </View>
            </TouchableWithoutFeedback>
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
