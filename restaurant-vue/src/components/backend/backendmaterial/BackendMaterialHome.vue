<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách nguyên vật liệu
      </div>
      <div class="list-body">
        <table class="list__table">
          <thead>
          <tr>
            <th>
              <input type="checkbox">
            </th>
            <th>
              STT
            </th>
            <th>
              Mã
            </th>
            <th>
              Tên
            </th>
            <th>
              Đơn vị tính
            </th>
            <th>
              Định lượng
            </th>
            <th>
              Lựa chọn
            </th>
          </tr>
          </thead>
          <tbody v-if="materials !== null">
          <tr v-for="(material, key, index) in materials"
              :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              <input type="text" v-model="material.materialName">
            </td>
            <td>
              <select v-if="material.optionType !== null" v-model="option.optionType">
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
        materials: null,
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
            this.materials = data;
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
