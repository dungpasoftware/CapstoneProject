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
          phone: '0824917021',
          password: 'sa123456'
        }
      };
    },
    methods: {
      _handleClickLogin() {
        this.$store.dispatch('login', this.loginData).then(res => {
          if (res.status === 200) {
            this.$router.push('/backend')
          }
        }).catch(err => {
          console.log(err);
        })
      },
      _handlePhoneChange(e) {
        e = (e) ? e : window.event;
        var charCode = (e.which) ? e.which : e.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          e.preventDefault();
        } else {
          return true;
        }
      }
    }
  }
</script>
