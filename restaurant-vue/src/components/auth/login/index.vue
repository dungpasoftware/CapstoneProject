<template>

  <div class="login">
    <div class="login-form animate__animated animate__bounceInDown animate__fast">
      <div class="login__title">
        Restaurant Management
      </div>
      <form @submit.prevent="_handleClickLogin" class="login-body">
        <div class="body-item">
          <label htmlFor="loginName">
            Số điện thoại
          </label>
          <input @keypress="_handlePhoneChange($event)" v-mask="'(###) ###-####'" v-model="loginData.phone"/>
        </div>
        <div class="body-item">
          <label htmlFor="loginPass">
            Mật khẩu
          </label>
          <input type="password" maxlength="14" minlength="8" v-model="loginData.password"/>
        </div>
        <div class="login-error">
          {{loginError}}
        </div>
        <div class="body-item">
          <button type="submit">Đăng nhập</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
  import {check_null, check_number, isLostConnect} from "../../../static";
  import cookies from "vue-cookies";

  export default {
    name: 'Login',
    data() {
      return {
        loginData: {
          phone: '',
          password: ''
        },
        loginError: null
      };
    },
    methods: {
      _handleClickLogin() {
        this.loginError = null;
        if (check_null(this.loginData.phone) || check_null(this.loginData.password)) {
          this.loginError = 'Hãy điền đầy đủ tài khoản và mật khẩu'
        } else {
          let requestData = {
            phone: this.loginData.phone.replace(/[-\s\(\)]/g, ''),
            password: this.loginData.password,
          }
          this.$store.dispatch('openLoader');
          this.$store.dispatch('login', requestData)
            .then(({data}) => {
              if (data.roleName === 'ROLE_CHEF' || data.roleName === 'ROLE_ORDER_TAKER') {
                this.loginError = 'Tài khoản hoặc mật khẩu không hợp lệ\n'
              } else {
                this.$cookies.set('user_token',data.token);
                this.$cookies.set('role_name', data.roleName);
                this.$cookies.set('staff_code', data.staffCode);
                this.$cookies.set('staff_id', data.staffId);

                this.$store.dispatch('addUserData', data);
                if (data.roleName === 'ROLE_MANAGER') {
                  this.$router.push({ name: 'backend-dish' }).catch(()=>{})
                } else if (data.roleName === 'ROLE_CASHIER') {
                  this.$router.push({ name: 'cashier' }).catch(()=>{})
                }
              }
            }).catch(error => {
              console.log(error)
            if (!isLostConnect(error, false, true)) {
                this.loginError = error.response.data.messages[0];
            }
          }).finally(() => {
            this.$store.dispatch('closeLoader');
          })
        }
      },
      _handlePhoneChange(e) {
        return check_number(e)
      }
    }
  }
</script>
