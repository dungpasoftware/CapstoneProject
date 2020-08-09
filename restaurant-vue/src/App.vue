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
    }
  }
</script>

<style>
  @import "assets/css/main.css";
  @import "assets/css/vue-select.css";
</style>
