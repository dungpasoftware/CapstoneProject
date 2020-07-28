<template>
  <div id="app">
    <Navbar/>
    <router-view/>
  </div>
</template>

<script>
  import Navbar from "./components/Navbar"


  export default {
    name: 'App',
    components: {Navbar},
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
