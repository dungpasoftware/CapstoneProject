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
    const [localError, setLocalError] = useState({
        haveError: false,
        message: ''
    })


    const userInfo = useSelector(state => state.loginReducer.userInfo)
    const isLoading = useSelector(state => state.loginReducer.isLoading)
    const messageServer = useSelector(state => state.loginReducer.messageServer)


    useEffect(() => {
        async function _checkStatusHandle() {
            if (messageServer == 'pass200') {
                switch (userInfo.role) {
                    case ROLE_ORDER_TAKER: {
                        dispatch(loadDish({ accessToken: userInfo.accessToken }))
                        navigation.navigate(LIST_TABLE_SCREEN, { userInfo })
                        setPhone('')
                        setPassword('')
                    }
                        break;
                    case ROLE_CHEF: {
                        navigation.navigate(KITCHEN_SCREEN, { userInfo })
                        setPhone('')
                        setPassword('')
                    }
                        break;
                    default:
                        break;
                }
            }
        }
        _checkStatusHandle()
    }, [messageServer])

    const dispatch = useDispatch()
    var vnPhone_regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
    var password_regex = /^[a-z]\w{7,14}$/;
    function handleLogin() {
        if (phone == "" || password == "") {
            setLocalError({
                haveError: true,
                message: 'Bạn chưa điền đủ thông tin để đăng nhập'
            })
            return
        }
        //!regular
        if (!vnPhone_regex.test(phone) || !password_regex.test(password)) {
            setLocalError({
                haveError: true,
                message: 'Tài khoản hoặc mật khẩu không đúng'
            })
            return
        }
        setLocalError({
            haveError: false,
            message: ''
        })
        dispatch(actionLogin({
            phone: phone,
            password: password
        }))
    }
    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle='dark-content' />
            <TouchableWithoutFeedback style={styles.container} onPress={Keyboard.dismiss}>
                <View style={styles.container}>
                    <View style={styles.logoContainer}>
                        <Image style={{
                            height: 100,
                            width: 100,
                            borderRadius: 40
                        }}
                            source={require('../../assets/logo.jpeg')}
                        />
                        <Text style={styles.title}>Order App</Text>
                    </View>
                    <View style={styles.infoContainer}>
                        <Text style={styles.titleInput}>Số điện thoại</Text>
                        <View style={styles.inputSide}>
                            <Feather name='phone' size={26} color='#24C3A3' />
                            <TextInput style={styles.input}
                                onChangeText={text => setPhone(text)}
                                placeholder="Nhập số điện thoại"
                                value={phone}
                                placeholderTextColor={"gray"}
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
                                value={password}
                                placeholderTextColor={"gray"}
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
                        {((messageServer != '' && messageServer != 'pass200') || localError.haveError) && <View style={{ flexDirection: 'row' }}>
                            <Feather name={'alert-triangle'} size={18} color='red' />
                            <Text style={{ color: 'red', fontSize: 16, marginLeft: 8 }}>{localError.haveError ? localError.message : messageServer}</Text>
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
