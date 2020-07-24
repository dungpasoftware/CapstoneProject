<template>
  <div id="app">
    <Navbar/>
    <router-view/>
  </div>
</template>

<script>
  import Navbar from "./components/Navbar"
  import * as types from "./store/mutations_type"


  export default {
    name: 'App',
    components: {Navbar},
    beforeCreate() {
      this.$store.commit(types.LOGIN, this.$cookies.get('staff_code'));

    }
  }
</script>

<style>
  @import "assets/css/main.css";
  /*@import '~vue-select/dist/vue-select.css';*/
</style>
