<template>
  <b-modal id="inventory_import_new" size="xl" hide-footer hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fad fa-file-import"></i>
        Phiếu nhập kho
      </div>
      <div class="modal-head__close" @click="$bvModal.hide('inventory_import_new')">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã phiếu <span class="starr"></span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Nhà cung cấp
          </label>
          <v-select :options="['Canada', 'United States']"></v-select>
        </div>
      </div>
      <div class="an-form">
        <div class="an-item">

        </div>
      </div>
      <div class="an-submit">
        <button class="btn-cancel" @click="$bvModal.hide('inventory_import_new')">Huỷ</button>
        <button class="btn-default-green">Tạo mới</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
  export default {
    name: 'BackendInventoryImportAddNew',
  }
</script>

<style scoped>

</style>
