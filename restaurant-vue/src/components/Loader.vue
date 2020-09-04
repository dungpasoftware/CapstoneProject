<template>
  <div :class="['loaderr', $store.getters.getLoader ? 'show' : '']">
    <div class="lds-spinner"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>

</style>
