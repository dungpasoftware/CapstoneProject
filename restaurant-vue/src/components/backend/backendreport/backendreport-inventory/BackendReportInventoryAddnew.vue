<template>
  <b-modal id="report_inventory_new" size="xl" hide-footer no-close-on-backdrop no-close-on-esc hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fas fa-plus"></i>
        Tạo mới kiểm kê
      </div>
      <div class="modal-head__close" @click="_handleCancelButton">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã phiếu
          </label>
          <select :defaultvalue="0" >
            <option :value="0" disabled selected>Chọn NVL</option>
            <option :value="1">
              Thịt bò
            </option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Mã phiếu
          </label>
          <input :maxlength="150">
        </div>
        <div class="an-item">
          <label>
            Tên NVL
          </label>
          <input type="text">
        </div>
        <div class="an-item">
          <label>
            Nhóm
          </label>
          <select :defaultvalue="0" >
            <option :value="0" disabled selected>Chọn nhóm</option>
            <option :value="1">
              Đồ ăn sáng
            </option>
          </select>
        </div>
      </div>
      <b-alert class="mt-4" v-model="formError.isShow" variant="danger" dismissible>
        <ul class="mb-0" v-if="formError.list.length > 0">
          <li v-for="(item, key) in formError.list" :key="key">
            {{item}}
          </li>
        </ul>
      </b-alert>
      <div class="an-submit">
        <button class="btn-cancel" @click="_handleCancelButton">Huỷ</button>
        <button class="btn-default-green">Tạo mới</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import {
  convert_code,
  check_null,
  check_number,
  isLostConnect,
  number_with_commas,
  remove_hyphen,
  mask_decimal,
  mask_number,
  mask_number_limit,
  mask_decimal_limit
} from "../../../../static";

  export default {
    name: 'BackendReportInventoryAddnew',
    data() {
      return {
        formError: {
          list: [],
          isShow: false
        },
        mask_decimal,
        mask_number,
        mask_number_limit,
        mask_decimal_limit,
      }
    },
    created() {
    },
    methods: {
      _handleCancelButton() {
        this.$bvModal.hide('report_inventory_new');
      }
    }
  }
</script>

<style scoped>

</style>
