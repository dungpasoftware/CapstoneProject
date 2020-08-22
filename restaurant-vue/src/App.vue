<template>
  <div id="app">
    <template v-if="!$store.getters.getLoadingCheckLogin">
      <Navbar/>
      <router-view/>
    </template>
    <CheckLogin v-if="$store.getters.getLoadingCheckLogin" />
    <Loader />
  </div>
</template>

<script>
  import Navbar from "./components/Navbar"
  import CheckLogin from "./components/auth/PreLogin"
  import Loader from "./components/Loader"

  export default {
    name: 'App',
    components: {CheckLogin, Navbar, Loader},
    beforeCreate() {
    }
  }
</script>

<style>
  @import "assets/css/main.css";
  @import "assets/css/vue-select.css";
</style>
