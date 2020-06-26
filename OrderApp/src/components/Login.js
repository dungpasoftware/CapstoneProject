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
                            <TextInput style={styles.input}
                                placeholder="Nhập username hoặc email"
                                placeholderTextColor='rgba(255,255,255,0.8)'
                                keyboardType='email-address'
                                returnKeyType="next"
                                autoCorrect={false}
                                onSubmitEditing={() => passwordRef.focus()}

                            />
                            <TextInput style={styles.input}
                                placeholder="Nhập password"
                                placeholderTextColor='rgba(255,255,255,0.8)'
                                returnKeyType="go"
                                secureTextEntry
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
        backgroundColor: '#24c3a3',
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
        color: '#f7c744',
        fontSize: 18,
        textAlign: "center",
        marginTop: 5,
        opacity: 0.9
    },
    infoContainer: {
        position: 'absolute',
        left: 0,
        right: 0,
        bottom: 50,
        height: 200,
        padding: 20,
    },
    input: {
        height: 40,
        backgroundColor: 'rgba(255,255,255,0.2)',
        color: '#fff',
        marginBottom: 20,
        paddingHorizontal: 10,
    },
    buttonContainer: {
        backgroundColor: '#f7c744',
        paddingVertical: 15
    },
    buttonText: {
        textAlign: 'center',
        color: 'rgb(32,53,70)',
        fontWeight: 'bold',
        fontSize: 18
    }

})
