<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách nhân viên
      </div>
      <div class="list-body">
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
              Đơn giá
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
              <span v-if="!option.isEdit">{{option.optionName}}</span>
              <input v-if="option.isEdit" type="text" v-model="option.optionName">
            </td>
            <td>
              <span v-if="!option.isEdit">
                {{(option.optionType === 'MONEY') ? 'Thêm tiền' : 'Không tính tiền'}}
              </span>
              <select v-if="option.optionType !== null && option.isEdit" v-model="option.optionType">
                <option value="0" disabled selected>Lựa chọn hình thức</option>
                <option value="MONEY">Thêm tiền</option>
                <option value="ADD">Không tính tiền</option>
<!--                <option value="SUB">Bớt tiền</option>-->
              </select>
            </td>
            <td>
              <span v-if="!option.isEdit">{{option.unit}}</span>
              <input v-if="option.isEdit" v-model="option.unit">
            </td>
            <td>
              <span v-if="!option.isEdit">{{option.price}}</span>
              <input v-if="option.isEdit" v-model="option.price" v-on:input="numberWithCommas($event)">
            </td>
            <td>
              <div v-if="!option.isEdit" class="table__option table__option-inline">
                <button @click="_handleButtonEnableEdit(key)"
                  class="btn-default-green btn-xs btn-yellow table__option--link">
                  <i class="fas fa-edit"></i>
                </button>
                <button class="btn-default-green btn-xs btn-red table__option--delete">
                  <i class="fas fa-trash-alt"></i>
                </button>
              </div>
              <div v-if="option.isEdit" class="table__option table__option-inline">
                <button @click="_handleButtonSaveClick(option)"
                        class="btn-default-green btn-xs table__option--link">
                  <i class="fas fa-check"></i>
                </button>
                <button @click="_handleButtonDisableEdit(key)"
                  class="btn-default-green btn-xs btn-gray table__option--delete">
                  <i class="far fa-times"></i>
                </button>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <span class="add-new" ><i class="fad fa-plus-circle"></i></span>
            </td>
            <td colspan="6"></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  import * as staticFunction from '../../../static'

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
      initOptions() {
        this.$store.dispatch('getAllOptions')
          .then(({data}) => {
            data = data.map(item => {
              item['isEdit'] = false;
              return item;
            })
            this.options = data;
          }).catch(error => {
        })
      },
      numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
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
      }
    }
  }
</script>
