<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách Topping
      </div>
      <div class="list-body">
        <div class="list__option">
          <router-link tag="button" :to="{ name: 'backend-option-addnew' }"
            class="btn-default-green">
            Thêm Topping
          </router-link>
        </div>
        <table class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Tên
            </th>
            <th>
              Hình thức
            </th>
            <th>
              Đơn vị
            </th>
            <th>
              Giá NVL
            </th>
            <th>
              Lựa chọn
            </th>
          </tr>
          </thead>
          <tbody v-if="options !== null">
          <tr v-for="(option, key, index) in options"
              :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              <span>{{(option.optionName !== null) ? option.optionName : ''}}</span>
            </td>
            <td>
              <span>
                {{
                  (option.optionType === 'MONEY') ? 'Thêm tiền' :
                  (option.optionType === 'ADD') ? 'Không tính tiền' : ''
                }}
              </span>
            </td>
            <td>
              <span>{{(option.unit !== null) ? option.unit : '- -'}}</span>
            </td>
            <td>
              <span>{{ (option.cost !== null && option.optionType === 'MONEY') ? `${number_with_commas(option.cost)}đ` : '- -' }}</span>
            </td>
            <td>
              <div v-if="!option.isEdit" class="table__option table__option-inline">
                <router-link tag="button" :to="{ name: 'backend-option-edit', params: { id: option.optionId } }"
                  class="btn-default-green btn-xs btn-yellow table__option--link">
                  <i class="fas fa-edit"></i>
                </router-link>
                <button @click="_handleButtonDeleteClick(option)"
                  class="btn-default-green btn-xs btn-red table__option--delete">
                  <i class="fas fa-trash-alt"></i>
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  import {number_with_commas} from "../../../static";

  export default {
    data() {
      return {
        options: null,
        optionIndex: 0,
      };
    },
    created() {
      this.initOptions();
    },
    methods: {
      number_with_commas,
      initOptions() {
        this.$store.dispatch('getAllOptions')
          .then(({data}) => {
            console.log(data)
            this.options = data;
          }).catch(error => {
          console.log(error)
        })
      },
      _handleButtonEnableEdit(key) {
        this.options[key].isEdit = true;
      },
      _handleButtonDisableEdit(key) {
        this.options[key].isEdit = false;
      },
      _handleButtonSaveClick(option) {
        if (option.isEdit) {
          this.$store.dispatch('editOptionById', option)
            .then(response => {
              this.initOptions();
              this.$swal('Thành công!',
                'Option đã được cập nhật lên hệ thống.',
                'success')
            }).catch(err => {
            console.error(err)
          })
        }
      },
      _handleButtonDeleteClick(option) {
        this.$swal(`Xoá "${option.optionName}"?`,
          'Bạn có chắc chắn muốn xoá.',
          'warning').then((result) => {
          if (result.value) {
            this.$store.dispatch('deleteOptionById', option.optionId)
              .then(response => {
                this.$swal({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Cập nhật topping thành công',
                  showConfirmButton: false,
                  timer: 1500
                })
                this.initOptions();
              }).catch(err => {
              console.error(err)
            })
          }
        })

      }
    }
  }
</script>
