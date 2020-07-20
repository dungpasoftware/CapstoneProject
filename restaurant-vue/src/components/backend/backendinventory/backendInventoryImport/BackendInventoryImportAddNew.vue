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
            Mã NVL <span class="starr"></span>
          </label>
          <input type="text" disabled>
        </div>
        <div class="an-item">
          <label>
            Tên NVL <span class="starr">*</span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Lượng hàng tồn <span class="starr">*</span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Hàng tồn tối thiểu <span class="starr">*</span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Phiếu nhập kho <span class="starr">*</span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Nhóm
          </label>
          <select>
            <option value="0">a</option>
            <option value="0">a</option>
            <option value="0">a</option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Nhà cung cấp
          </label>
          <select>
            <option value="0">a</option>
            <option value="0">a</option>
            <option value="0">a</option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Kho
          </label>
          <select>
            <option value="0">a</option>
            <option value="0">a</option>
            <option value="0">a</option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Đơn vị nhập <span class="starr">*</span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Đơn vị xuất <span class="starr">*</span>
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Đơn vị nhập / đơn vị xuất <span class="starr">*</span>
          </label>
          <div class="double-input">
            <span>1</span>
            <span>Kg</span>
            <span class="pr-2">=</span>
            <input type="text">
            <span class="pl-2">Gram</span>
          </div>
        </div>
        <div class="an-item">
          <label>
            Giá nhập (Đơn vị nhập) <span class="starr">*</span>
          </label>
          <div class="left-input">
            <input type="text">
            <span>/</span>
            <div>Kg</div>
          </div>
        </div>
        <div class="an-item">
          <label>
            Giá nhập (Đơn vị xuất)
          </label>
          <div class="left-input">
            <input type="text" disabled>
            <span>/</span>
            <div>Gram</div>
          </div>
        </div>
        <div class="an-item">
          <label>
            Tổng giá nhập
          </label>
            <input type="text">
        </div>
        <div class="an-item">
          <label>
            Hạn sử dụng
          </label>
          <input type="date">
        </div>
        <div class="an-item">
          <label>
            Ghi chú
          </label>
          <textarea rows="3"></textarea>
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
