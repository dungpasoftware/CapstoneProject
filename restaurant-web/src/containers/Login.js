import React, {Component} from 'react';
import {loginUser} from "../networks/Auth";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loginData: {
                username: '',
                password: ''
            }
        }
    }

    componentDidMount() {
        if (this.props.userAuth === null) {

        }
    }

    _handleUsernameChange = (e) => {
        let loginData = this.state.loginData;
        loginData.username = e.target.value;
        this.setState({ loginData });
        console.log(this.state.loginData);
    }

    _handlePasswordChange = (e) => {
        let loginData = this.state.loginData;
        loginData.password = e.target.value;
        this.setState({ loginData });
        console.log(this.state.loginData);
    }

    _handleClickLoginButton = (e) => {
        loginUser(this.state.loginData);
    }

    render() {
        return (
            <div className="login">
                <div className="login-form animate__animated animate__bounceInDown animate__fast">
                    <div className="login__title">
                        Restaurant Management
                    </div>
                    <div className="login-body">
                        <div className="body-item">
                            <label htmlFor="loginName">
                                Số điện thoại
                            </label>
                            <input onChange={this._handleUsernameChange}
                                   type="text" itemID={'loginName'}/>
                        </div>
                        <div className="body-item">
                            <label htmlFor="loginPass">
                                Mật khẩu
                            </label>
                            <input onChange={this._handlePasswordChange}
                                type="password" itemID={'loginPass'}/>
                        </div>
                        <div className="body-item">
                            <button onClick={this._handleClickLoginButton}>Đăng nhập</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;