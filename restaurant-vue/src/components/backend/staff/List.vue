<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-user"></i>
        Danh sách nhân viên
      </div>
      <div class="list-body">
        <div class="list__option">
          <button @click="$bvModal.show('staff_new')" class="btn-default-green">
            <i class="fas fa-plus"></i>
            Thêm nhân viên mới
          </button>
        </div>
        <table v-if="staffs && staffs.length > 0" class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Mã nhân viên
            </th>
            <th>
              Họ tên
            </th>
            <th>
              Số điện thoại
            </th>
            <th>
              Email
            </th>
            <th>
              Địa chỉ
            </th>
            <th>
              Chức vụ
            </th>
            <th>
              Lựa chọn
            </th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(staff, key) in staffs" :key="key">
            <template v-if="staff.isActivated === 1">
              <td>
                {{ key + 1 }}
              </td>
              <td>
                {{ (staff.staffCode) ? staff.staffCode : '- -' }}
              </td>
              <td>
                {{ (staff.fullname) ? staff.fullname : '- -' }}
              </td>
              <td>
                {{ (staff.phone) ? staff.phone : '- -' }}
              </td>
              <td>
                {{ (staff.email) ? staff.email : '- -' }}
              </td>
              <td>
                {{ (staff.addrress) ? staff.addrress : '- -' }}
              </td>
              <td>
                <template v-if="staff.roleName">
                  {{ (staff.roleName === 'MANAGER') ? 'Quản lý' : '' }}
                  {{ (staff.roleName === 'CASHIER') ? 'Thu ngân' : '' }}
                  {{ (staff.roleName === 'CHEF') ? 'Đầu bếp' : '' }}
                  {{ (staff.roleName === 'ORDER_TAKER') ? 'Bồi bàn' : '' }}
                </template>
              </td>
              <td>
                <div class="table__option table__option-inline">
                  <button @click="_handleDeleteStaff(staff.staffId)"
                          class="btn-default-green btn-xs btn-red table__option--delete">
                    <i class="fas fa-trash-alt"></i>
                  </button>
                </div>
              </td>
            </template>
          </tr>
          </tbody>
        </table>
        <div v-else class="text-center">
          Danh sách trống
        </div>
      </div>
    </div>
    <AddNewStaff :initStaffs="initStaffs"/>
    <ChangePassword/>
  </div>
</template>

<script>
import AddNewStaff from './Add'
import ChangePassword from './ChangePassword'
import {isLostConnect} from "../../../static";

export default {
  components: {
    AddNewStaff
  },
  data() {
    return {
      staffs: null,
    };
  },
  created() {
    this.initStaffs();
  },
  methods: {
    initStaffs() {
      this.$store.dispatch('openLoader');
      this.$store.dispatch('getAllStaff')
        .then(({data}) => {
          console.log(data);
          this.staffs = data;
        }).catch(error => {
        if (!isLostConnect(error)) {
        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    _handleDeleteStaff(staffId) {
      // this.$swal({
      //   title: `Xoá nhân viên?`,
      //   html: 'Bạn có chắc chắn muốn xoá.',
      //   icon: 'warning',
      //   confirmButtonText: 'Xoá',
      //   confirmButtonColor: '#F05348',
      //   showCancelButton: true,
      //   cancelButtonText: 'Huỷ',
      //   showCloseButton: true
      // }).then((result) => {
      //   if (result.value) {
          // this.$store.dispatch('openLoader');
          // this.$store.dispatch('deleteOptionById', option.optionId)
          //   .then(response => {
          //     this.$swal({
          //       position: 'top-end',
          //       icon: 'success',
          //       title: 'Xoá nhân viên thành công',
          //       showConfirmButton: false,
          //       timer: 5000,
          //       toast: true
          //     })
          //     this.initStaffs();
          //   }).catch(error => {
          //   if (!isLostConnect(error, false)) {
          //     this.$swal({
          //       title: 'Có lỗi xảy ra',
          //       html: 'Vui lòng thử lại',
          //       icon: 'warning',
          //       showCloseButton: true,
          //       confirmButtonText: 'Đóng',
          //     });
          //   }
          // }).finally(() => {
          //   this.$store.dispatch('closeLoader');
          // })
      //   }
      // })
      this.$swal({
              position: 'top-end',
              icon: 'info',
              title: 'Tính năng đang được cập nhật, vui lòng thử lại sau',
              showConfirmButton: false,
              timer: 5000,
              toast: true
            })
    }
  }
}
</script>
