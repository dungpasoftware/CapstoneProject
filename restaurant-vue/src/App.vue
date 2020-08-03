<template>
  <div id="app">
    <template v-if="!$store.getters.getLoadingCheckLogin">
      <Navbar/>
      <router-view/>
    </template>
    <CheckLogin v-if="$store.getters.getLoadingCheckLogin" />
  </div>
</template>

<script>
  import Navbar from "./components/Navbar"
  import CheckLogin from "./components/auth/PreLogin"

  export default {
    name: 'App',
    components: {CheckLogin, Navbar},
    beforeCreate() {
      let userData = {
        token: this.$cookies.get('user_token'),
        roleName: this.$cookies.get('role_name'),
        staffCode: this.$cookies.get('staff_code'),
        staffId: this.$cookies.get('staff_id')
      };
      this.$store.dispatch('addUserData', userData);
    }
  }
</script>

<style>
  @import "assets/css/main.css";
  @import "assets/css/vue-select.css";
</style>
