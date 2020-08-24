<template>
  <div>
<!--    <button @click="fakeToken">Fake token</button>-->
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

  export default {
    name: 'BackendHome',
    computed: {
      ...mapGetters(['getResult'])
    },
    methods: {
      fakeToken() {
        this.$cookies.set('user_token','ffhf');
      }
    }

  }
</script>
