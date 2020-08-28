<template>
  <b-modal id="staff_new" size="md" hide-footer no-close-on-backdrop no-close-on-esc
           hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fal fa-clipboard-user"></i>
        Thêm nhân viên mới
      </div>
      <div class="modal-head__close" @click="_handleCancelButton">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Số điện thoại <span class="starr">*</span>
          </label>
          <input v-mask="'(###) ###-####'" v-model="staffData.phone">
        </div>
        <div class="an-item">
          <label>
            Email <span class="starr">*</span>
          </label>
          <input type="email" maxlength="80" v-model="staffData.email">
        </div>
        <div class="an-item">
          <label>
            Họ tên đầy đủ <span class="starr">*</span>
          </label>
          <input maxlength="80" v-model="staffData.fullName" @input="_handleNameInput">
        </div>
        <div class="an-item">
          <label>
            Địa chỉ
          </label>
          <input maxlength="80" v-model="staffData.address">
        </div>
        <div class="an-item">
          <label>
            Chức vụ
          </label>
          <select v-model="staffData.role">
            <option :value="1">Quản lý</option>
            <option :value="2">Thu ngân</option>
            <option :value="3">Đầu bếp</option>
            <option :value="4">Bồi bàn</option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Mật khẩu <span class="starr">*</span>
          </label>
          <input type="password" minlength="8" maxlength="14" v-model="staffData.password">
        </div>
        <div class="an-item">
          <label>
            Nhập lại mật khẩu <span class="starr">*</span>
          </label>
          <input type="password" minlength="8" maxlength="14" v-model="staffData.confirmPassword">
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
        <button class="btn-cancel" @click="_handleCancelButton">Huỷ</button>
        <button class="btn-default-green" @click="_handleAddnewButton">Thêm nhân viên mới</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import {
  check_null,
  convert_code, isLostConnect, validateEmail
} from "../../../static";

export default {
  name: 'BackendInventoryImportAddNew',
  props: [
    'initStaffs'
  ],
  data() {
    return {
      staffData: {
        phone: null,
        email: null,
        staffCode: null,
        fullName: null,
        address: null,
        role: 1,
        password: null,
        confirmPassword: null,
      },
      formError: {
        list: [],
        isShow: false
      }
    }
  },
  created() {
  },
  methods: {
    initStaff() {
      this.staffData = {
        phone: null,
        email: null,
        staffCode: null,
        fullName: null,
        address: null,
        role: 1,
        password: null,
        confirmPassword: null,
      }
    },
    _handleNameInput() {
      this.staffData.staffCode = convert_code(this.staffData.fullName);
    },
    _handleAddnewButton() {
      let requestData = {
        phone: (!check_null(this.staffData.phone)) ? this.staffData.phone.replace(/[-\s\(\)]/g, '') : '',
        email: this.staffData.email,
        fullname: this.staffData.fullName,
        address: this.staffData.address,
        roleId: this.staffData.role,
        password: this.staffData.password
      }
      this.formError = {
        list: [],
        isShow: false
      }
      if (check_null(requestData.phone)) {
        this.formError.list.push('Số điện thoại không được để trống');
        this.formError.isShow = true;
      } else if (requestData.phone.length !== 10 || requestData.phone[0] !== '0') {
        this.formError.list.push('Số điện thoại không hợp lệ');
        this.formError.isShow = true;
      }
      if (check_null(requestData.email)) {
        this.formError.list.push('Email không được để trống');
        this.formError.isShow = true;
      } else if (!validateEmail(requestData.email)) {
        this.formError.list.push('Email không hợp lệ');
        this.formError.isShow = true;
      }
      if (check_null(requestData.fullname)) {
        this.formError.list.push('Họ tên không được để trống');
        this.formError.isShow = true;
      }
      if (check_null(this.staffData.password)) {
        this.formError.list.push('Mật khẩu không được để trống');
        this.formError.isShow = true;
      } else if (this.staffData.password.length < 8 || this.staffData.password.length > 14) {
        this.formError.list.push('Mật khẩu từ 8 đến 14 kí tự');
        this.formError.isShow = true;
      } else if (check_null(this.staffData.confirmPassword)) {
        this.formError.list.push('Mật khẩu nhập lại không được để trống');
        this.formError.isShow = true;
      } else if (this.staffData.confirmPassword !== this.staffData.password) {
        this.formError.list.push('Mật khẩu và phần nhập lại không trùng khớp');
        this.formError.isShow = true;
      }
      if (check_null(this.staffData.confirmPassword)) {
        this.formError.list.push('Mật khẩu nhập lại không được để trống');
        this.formError.isShow = true;
      }
      if (!this.formError.isShow) {
        this.$store.dispatch('openLoader');
        this.$store.dispatch('addNewStaff', requestData)
          .then(response => {
            this.$swal('Thành công!',
              'Nhân viên mới đã được cập nhật lên hệ thống.',
              'success').then((result) => {
                this.initStaffs();
                this._handleCancelButton();
            })
          }).catch(error => {
          if (!isLostConnect(error, false)) {
            if (error.response.data && error.response.data.messages && error.response.data.messages.length > 0) {
              error.response.data.messages.map(item => {
                this.formError.list.push(item);
                this.formError.isShow = true;
              })
            }
          }
        }).finally(() => {
          this.$store.dispatch('closeLoader');
        })
      }
    },
    _handleCancelButton() {
      this.formError = {
        list: [],
        isShow: false
      }
      this.staffData = {
        phone: null,
        email: null,
        staffCode: null,
        fullName: null,
        address: null,
        role: 1,
        password: null,
        confirmPassword: null,
      }
      this.$bvModal.hide('staff_new');
    }
  }
}
</script>

<style scoped>

</style>
