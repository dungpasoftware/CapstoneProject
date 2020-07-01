<template>
  <div>
    day la state: {{getResult}}
    <hr>
    day la getters:
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

  export default {
    name: 'BackendHome',
    computed: {
      ...mapGetters(['getResult'])
    }

  }
</script>
