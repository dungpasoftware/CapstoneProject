<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách Topping
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
              <input type="text" v-model="option.optionName">
            </td>
            <td>
              <select v-if="option.optionType !== null" v-model="option.optionType">
                <option value="MONEY">Thêm tiền</option>
                <option value="ADD">Không tính tiền</option>
                <option value="SUB">Bớt tiền</option>
              </select>
            </td>
            <td>
              <input v-model="option.unit">
            </td>
            <td>
              <input v-model="option.price" v-on:input="numberWithCommas($event)">
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleButtonSaveClick(option)"
                  class="btn-default-green btn-xs btn-yellow table__option--link">
                  Chỉnh sửa
                </button>
                <button class="btn-default-green btn-xs btn-red table__option--delete">Xoá</button>
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
            this.options = data;
          }).catch(error => {
          console.log(error)
        })
      },
      numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      },
      _handleButtonSaveClick(option) {
        this.$store.dispatch('editOptionById', option)
          .then(response => {
            this.initOptions();
            alert('Success')
          }).catch(err => {
            console.error(err)
        })
      }
    }
  }
</script>
