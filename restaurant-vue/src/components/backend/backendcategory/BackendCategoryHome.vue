<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách nhóm thực đơn
      </div>
      <div class="list-body">
        <div class="list__option">
          <button @click="_handleButtonAddnew" class="btn-default-green" v-if="categoryAddnew === null">
            Tạo mới nhóm thực đơn
          </button>
        </div>
        <table v-if="categories && categories.length > 0" class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Tên
            </th>
            <th>
              Mức độ ưu tiên
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
          <tr>

            <template v-if="categoryAddnew !== null">
              <td></td>
              <td>
                <input type="text" placeholder="Nhập tên nhóm thực đơn" :maxlength="100"
                       v-model="categoryAddnew.categoryName">
              </td>
              <td>
                <select :defaultvalue="1" v-model="categoryAddnew.priority">
                  <option :value="1">Ưu tiên nhất</option>
                  <option :value="2">Ưu tiên vừa</option>
                  <option :value="3">Bình thường</option>
                  <option :value="4">Ưu tiên thấp</option>
                  <option :value="5">Rất nhất</option>
                </select>
              </td>
              <td>
                <textarea placeholder="Nhập mô tả" :maxlength="200"
                          v-model="categoryAddnew.description"></textarea>
              </td>
              <td>
                <div class="table__option table__option-inline">
                  <button @click="_handleButtonAddnewSave"
                          class="btn-default-green btn-xs table__option--link">
                    <i class="fas fa-check"></i>
                  </button>
                  <button @click="_handleButtonDisableAddnew"
                          class="btn-default-green btn-xs btn-gray table__option--delete">
                    <i class="far fa-times"></i>
                  </button>
                </div>
              </td>
            </template>
          </tr>
          <tr v-for="(category, key, index) in categories"
              :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              <span v-if="!categoryEdit || categoryEdit.categoryIndex !== key">{{category.categoryName}}</span>
              <input v-if="categoryEdit && categoryEdit.categoryIndex === key" type="text" placeholder="Nhập tên nhóm thực đơn"
                     :maxlength="200" v-model="categoryEdit.categoryName">
            </td>
            <td>
              <span v-if="(!categoryEdit || categoryEdit.categoryIndex !== key) && category.priority !== null">
                <template v-if="category.priority === 1">Ưu tiên nhất</template>
                <template v-if="category.priority === 2">Ưu tiên vừa</template>
                <template v-if="category.priority === 3">Bình thường</template>
                <template v-if="category.priority === 4">Ưu tiên thấp</template>
                <template v-if="category.priority === 5">Rất nhất</template>
              </span>
              <select v-if="categoryEdit && categoryEdit.categoryIndex === key"
                      v-model="categoryEdit.priority">
                <option :value="1">Ưu tiên nhất</option>
                <option :value="2">Ưu tiên vừa</option>
                <option :value="3">Bình thường</option>
                <option :value="4">Ưu tiên thấp</option>
                <option :value="5">Rất nhất</option>
              </select>
            </td>
            <td>
              <span v-if="!categoryEdit || categoryEdit.categoryIndex !== key">{{category.description}}</span>
              <textarea v-if="categoryEdit && categoryEdit.categoryIndex === key" placeholder="Nhập mô tả"
                        :maxlength="200" v-model="categoryEdit.description"></textarea>
            </td>
            <td>
              <div v-if="!categoryEdit || categoryEdit.categoryIndex !== key" class="table__option table__option-inline">
                <button @click="_handleButtonEnableEdit(category, key)"
                        class="btn-default-green btn-xs btn-yellow table__option--link">
                  <i class="fas fa-edit"></i>
                </button>
                <button @click="_handleButtonDeleteClick(category)"
                  class="btn-default-green btn-xs btn-red table__option--delete">
                  <i class="fas fa-trash-alt"></i>
                </button>
              </div>
              <div v-if="categoryEdit && categoryEdit.categoryIndex === key" class="table__option table__option-inline">
                <button @click="_handleButtonSaveClick(category)"
                        class="btn-default-green btn-xs table__option--link">
                  <i class="fas fa-check"></i>
                </button>
                <button @click="_handleButtonDisableEdit(category)"
                        class="btn-default-green btn-xs btn-gray table__option--delete">
                  <i class="far fa-times"></i>
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div v-else class="text-center">
          Dữ liệu trống
        </div>
        <b-alert class="mt-4" v-model="formError.isShow" variant="danger" dismissible>
          <ul class="mb-0" v-if="formError.list.length > 0">
            <li v-for="(item, key) in formError.list" :key="key">
              {{item}}
            </li>
          </ul>
        </b-alert>
      </div>
    </div>
  </div>
</template>

<script>
  import {check_null, isLostConnect} from "../../../static";

  export default {
    data() {
      return {
        categories: null,
        categoryIndex: 0,
        categoryAddnew: null,
        categoryEdit: null,
        formError: {
          list: [],
          isShow: false
        },
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
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
      },
      numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      },
      _handleButtonAddnew() {
        this.categoryEdit = null;
        this.categoryAddnew = {
          categoryName: '',
          description: '',
          priority: 1,
        }
      },
      _handleButtonDisableAddnew() {
        this.categoryAddnew = null;
      },
      _handleButtonEnableEdit(category, key) {
        this.categoryAddnew = null;
        this.categoryEdit = {
          categoryIndex: key,
          ...category
        }
      },
      _handleButtonDisableEdit(key) {
        this.categoryEdit = null;
      },
      _handleButtonAddnewSave() {
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(this.categoryAddnew.categoryName)) {
          this.formError.list.push('Tên nhóm thực đơn không được để trống');
          this.formError.isShow = true;
        }
        if (!this.formError.isShow) {
          this.$store.dispatch('addNewCategory', this.categoryAddnew)
            .then(response => {
              this.initCategories();
              this._handleButtonDisableAddnew();
              this.$swal('Thành công!',
                'Nhóm thực đơn đã được cập nhật lên hệ thống.',
                'success')
            }).catch(error => {
            if (!isLostConnect(error, false)) {
              error.response.data.messages.map(err => {
                this.formError.list.push(err);
                this.formError.isShow = true;
              })
            }
          })
        }
      },
      _handleButtonSaveClick() {
        let category = this.categoryEdit;
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(category.categoryName)) {
          this.formError.list.push('Tên nhóm thực đơn không được để trống');
          this.formError.isShow = true;
        }
        if (!this.formError.isShow) {
          this.$store.dispatch('editCategoryById', category)
            .then(response => {
              this.initCategories();
              this._handleButtonDisableEdit();
              this.$swal('Thành công!',
                'Nhóm thực đơn đã được cập nhật lên hệ thống.',
                'success')
            }).catch(error => {
            if (!isLostConnect(error, false)) {
              error.response.data.messages.map(err => {
                this.formError.list.push(err);
                this.formError.isShow = true;
              })
            }
          })
        }
      },
      _handleButtonDeleteClick(category) {
        this.$swal(`Xoá ${category.categoryName}?`,
          'Bạn có chắc chắn muốn xoá.',
          'warning').then((result) => {
          if (result.value) {
            this.$store.dispatch('deleteCategoryById', category.categoryId)
              .then(response => {
                this.$swal({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Cập nhật danh sách thành công',
                  showConfirmButton: false,
                  timer: 1500
                })
                this.initCategories();
              }).catch(error => {
              if (!isLostConnect(error, false)) {
                error.response.data.messages.map(err => {
                  this.formError.list.push(err);
                  this.formError.isShow = true;
                })
              }
            })
          }
        })
      }
    }
  }
</script>
