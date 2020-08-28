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
              Email
            </th>
            <th>
              Số điện thoại
            </th>
            <th>
              Họ tên
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
                {{ (staff.email) ? staff.email : '- -' }}
              </td>
              <td>
                {{ (staff.phone) ? staff.phone : '- -' }}
              </td>
              <td>
                {{ (staff.fullname) ? staff.fullname : '- -' }}
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
                  <button @click="$bvModal.show('staff_change_pass')"
                          class="btn-default-green btn-xs btn-yellow table__option--link">
                    Đổi mật khẩu
                  </button>
                  <button class="btn-default-green btn-xs btn-red table__option--delete">
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
    <AddNewStaff :initStaffs="initStaffs" />
    <ChangePassword />
  </div>
</template>

<script>
  import AddNewStaff from './Add'
  import ChangePassword from './ChangePassword'
  import {isLostConnect} from "../../../static";

  export default {
    components: {
      AddNewStaff,
      ChangePassword
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
    }
  }
</script>
