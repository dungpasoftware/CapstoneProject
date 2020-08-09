<template>

  <div class="check-login">
    <div class="check-login-form animate__animated animate__fadeIn animate__fast">
      <div class="login__title">
        Restaurant Management
      </div>
      <div class="loading">
        <div class="hollow-dots-spinner">
          <div class="dot"></div>
          <div class="dot"></div>
          <div class="dot"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {check_null, check_number} from "../../static";
  import cookies from "vue-cookies";

  export default {
    name: 'CheckLogin',
    data() {
      return {
        loginData: {
          phone: '0824917022',
          password: 'sa123456'
        },
        loginError: null
      };
    },
    created() {
      this.preLogin();
    },
    methods: {
      preLogin() {
        setTimeout(() => {
          if (this.$cookies.get('user_token') !== null) {
            this.$store.dispatch('checkLogin')
              .then(response => {
                let userData = {
                  token: response.data.token,
                  roleName: response.data.roleName,
                  staffCode: response.data.staffCode,
                  staffId: response.data.staffId
                };
                this.$store.dispatch('addUserData', userData);
                console.log(response.data)
              }).catch(err => {
              this.$store.dispatch('logout');
            }).finally(() => {
              this.$store.dispatch('closeLoading');
            })
          } else {
            this.$store.dispatch('closeLoading');
          }
        }, 1000)
      },
    }
  }
</script>
