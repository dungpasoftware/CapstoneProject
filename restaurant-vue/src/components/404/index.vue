<template>
  <div class="rs-body">
    <div class="error-page">
      <div class="error-inner">
        <i class="fad fa-exclamation-triangle"></i>
        <div class="error-title">
          Trang không tồn tại!
        </div>
        <div class="error-text">
          Vui lòng kiểm tra lại
        </div>
        <div class="back-home">
          <router-link tag="button" :to="{ name: 'backend-dish' }" class="btn-default-green">
            Quay về trang chủ
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>
