<template>
  <b-modal id="staff_change_pass" @hidden="_handleCloseModal" size="md" hide-footer
           hide-header centered>
    <div class="modal-head modal-head__yellow">
      <div class="modal-head__title">
        <i class="fal fa-clipboard-user"></i>
        Đổi mật khẩu
      </div>
      <div class="modal-head__close" @click="_handleCloseModal">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mật khẩu cũ <span class="starr">*</span>
          </label>
          <input maxlength="100">
        </div>
        <div class="an-item">
          <label>
            Mật khẩu mới <span class="starr">*</span>
          </label>
          <input maxlength="100">
        </div>
        <div class="an-item">
          <label>
            Nhập lại mật khẩu <span class="starr">*</span>
          </label>
          <input maxlength="100">
        </div>
      </div>
      <b-alert class="mt-4" v-model="formError.isShow" variant="danger" dismissible>
        <ul class="mb-0" v-if="formError.list.length > 0">
          <li v-for="(item, key) in formError.list" :key="key">
            {{ item }}
          </li>
        </ul>
      </b-alert>
      <div class="an-submit">
        <button class="btn-cancel" @click="_handleCloseModal">Huỷ</button>
        <button class="btn-default-green btn-yellow">Đổi mật khẩu</button>
      </div>
    </div>
  </b-modal>
</template>

<script>

  import {
    check_null, number_with_commas,
  } from "../../../static";

  export default {
    name: 'BackendReportOrderBill',
    data() {
      return {
        formError: {
          list: [],
          isShow: false
        }
      }
    },
    props: [],
    created() {
    },
    methods: {
      check_null,
      number_with_commas,
      eventCloseModal() {
      },
      _handleCloseModal() {
        this.$bvModal.hide('staff_change_pass');
      },
    }
  }
</script>
