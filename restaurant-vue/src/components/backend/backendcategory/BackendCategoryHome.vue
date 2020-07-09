<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách nhóm thực đơn
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
              Hình ảnh
            </th>
            <th>
             Mô tả
            </th>
            <th>
              Lựa chọn
            </th>
          </tr>
          </thead>
          <tbody v-if="categories !== null">
          <tr v-for="(category, key, index) in categories"
              :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              <input type="text" v-model="category.categoryName">
            </td>
            <td>
              <template v-if="category.imageUrl !== null">
                <img :src="category.imageUrl" alt="">
              </template>
            </td>
            <td>
              <textarea v-model="category.description"></textarea>
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleButtonSaveClick(category)"
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
        categories: null,
        categoryIndex: 0,
      };
    },
    created() {
      this.initCategories();
    },
    methods: {
      initCategories() {
        this.$store.dispatch('getAllCategories')
          .then(({data}) => {
            this.categories = data;
            console.log(this.categories);
          }).catch(error => {
          console.log(error)
        })
      },
      numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      },
      _handleButtonSaveClick(category) {
        this.$store.dispatch('editCategoryById', category)
          .then(response => {
            this.initCategories();
            alert('Success')
          })
      }
    }
  }
</script>
