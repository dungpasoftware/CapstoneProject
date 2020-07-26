<template>

  <div class="login">
    <div class="login-form animate__animated animate__bounceInDown animate__fast">
      <div class="login__title">
        Restaurant Management
      </div>
      <div class="login-body">
        <div class="body-item">
          <label htmlFor="loginName">
            Số điện thoại
          </label>
          <input @keypress="_handlePhoneChange($event)" v-model="loginData.phone"/>
        </div>
        <div class="body-item">
          <label htmlFor="loginPass">
            Mật khẩu
          </label>
          <input type="password" v-model="loginData.password"/>
        </div>
        <div class="login-error">
          {{loginError}}
        </div>
        <div class="body-item">
          <button @click="_handleClickLogin">Đăng nhập</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {check_null, check_number} from "../../static";
  import cookies from "vue-cookies";

  export default {
    name: 'Login',
    data() {
      return {
        loginData: {
          phone: '0824917022',
          password: 'sa123456'
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
          this.$store.dispatch('login', this.loginData)
            .then(({data}) => {
              if (data.roleName === 'ROLE_CHEF' || data.roleName === 'ROLE_ORDER_TAKER') {
                this.loginError = 'Sai tài khoản hoặc mật khẩu'
              } else {
                this.$cookies.set('user_token',data.token);
                this.$cookies.set('user_name', data.roleName);
                this.$cookies.set('staff_code', data.staffCode);
                this.$cookies.set('staff_id', data.staffId);
                if (data.roleName === 'ROLE_MANAGER') {
                  this.$router.push({ name: 'backend' })
                } else if (data.roleName === 'ROLE_CASHIER') {
                  this.$router.push({ name: 'backend-cashier' })
                }
              }
            }).catch(err => {
              if (err.response.status === 401) {
                this.loginError = 'Sai tài khoản hoặc mật khẩu'
              }
          })
        }
      },
      _handlePhoneChange(e) {
        return check_number(e)
      }
    }
  }
</script>
