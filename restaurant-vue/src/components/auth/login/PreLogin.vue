<template>

  <div class="check-login">
    <div class="check-login-form animate__animated animate__fadeIn animate__fast">
      <div class="login__title">
        Restaurant Management
      </div>
      <div class="show-error" v-if="loginError !== null">
        {{ loginError }}
      </div>
      <div class="loading" v-if="loginError === null">
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
  import {check_null, check_number, isLostConnect} from "../../../static";
  import cookies from "vue-cookies";

  export default {
    name: 'CheckLogin',
    data() {
      return {
        loginData: {
          phone: '0824917022',
          password: 'sa123456'
        },
        loginError: null,

      };
    },
    created() {
      this.preLogin();
    },
    methods: {
      preLogin() {
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
              this.$store.dispatch('closeCheckLogin');
            }).catch(err => {
            if (!isLostConnect(err)) {
              if (err.response) {
                if (err.response.status === 401) {
                  this.$store.dispatch('logout');
                  this.$store.dispatch('closeCheckLogin');
                  this.$router.push({name: 'login'})
                }
              }
            }
          })
        } else {
          this.$store.dispatch('closeCheckLogin');
        }
      },
    }
  }
</script>
