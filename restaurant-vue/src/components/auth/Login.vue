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
          <input type="number" v-model="loginData.phone"/>
        </div>
        <div class="body-item">
          <label htmlFor="loginPass">
            Mật khẩu
          </label>
          <input type="password" v-model="loginData.password"/>
        </div>
        <div class="body-item">
          <button @click="_handleClickLogin">Đăng nhập</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        loginData: {
          phone: '',
          password: ''
        }
      };
    },
    methods: {
      _handleClickLogin() {
        this.$store.dispatch('login', this.loginData);
      }
    }
  }
</script>
