<template>
  <div>
    <div class="be-select">
      <form @submit.prevent="_handleButtonSearch" class="be-select--left">
        <input v-model="dishSearch.default" type="text" class="select__name"
               @input="_handleDishSearchChange"
               placeholder="Tên món ăn"/>
        <select v-model="categoryIndex" defaultValue="0" class="select__type">
          <option :value="0">Tất cả</option>
          <template v-if="categories !== null">
            <option v-for="(category, key) in categories" :value="category.categoryId" :key="key">
              {{ (category.categoryName !== null) ? category.categoryName : '' }}
            </option>
          </template>
        </select>
        <button type="submit" class="select__search btn-default-green">
          Tìm kiếm
        </button>
      </form>
      <div class="be-select--right">
        <router-link tag="button" :to="{name: 'backend-dish-addnew'}" class="select__add-new btn-default-green">
          Thêm món mới
        </router-link>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-pizza"/>
        Danh sách món
      </div>
      <div class="list-body">
        <template v-if="dishes && dishes.length > 0">
          <div class="list__option">
            <button @click="_handleDeleteMany" class="btn-default-green btn-red">
              Xoá danh sách đã chọn
            </button>
          </div>
          <table class="list__table">
            <thead>
            <tr>
              <th>
                <input type="checkbox" v-model="isSelectedAll" @change="_handleSelectAll"/>
              </th>
              <th> Mã sản phẩm</th>
              <th> Hình đại diện</th>
              <th> Tên món</th>
              <th> Nhóm thực đơn</th>
              <th> Giá nguyên vật liệu</th>
              <th> Giá bán / đơn vị</th>
              <th> Lựa chọn</th>
            </tr>
            </thead>
            <tbody v-if="dishes !== null">
            <tr v-for="(dish, key) in dishes"
                :key="key">
              <td>
                <input type="checkbox" v-model="dish.isSelected" @click="_handleSelectItem(key)"/>
              </td>
              <td>
                {{ (dish.dishCode !== null) ? dish.dishCode : '- -' }}
              </td>
              <td>
                <template v-if="dish.imageUrl !== null">
                  <img :src="dish.imageUrl" alt="">
                </template>
                <template v-else>
                  - -
                </template>
              </td>
              <td>
                {{ (dish.dishName !== null) ? dish.dishName : '- -' }}
              </td>
              <td>
                <div v-if="dish.categories !== null && dish.categories.length > 0"
                     v-for="(category, key) in dish.categories"
                     style="white-space: nowrap">
                  {{ category.categoryName }}
                </div>
                <div v-else>
                  - -
                </div>
              </td>
              <td>
                {{ (dish.cost !== null) ? numberWithCommas(Math.ceil(dish.cost)) : '- -' }}đ
              </td>
              <td>
                <template v-if="dish.defaultPrice !== null && dish.dishUnit !== null">
                  {{ (dish.defaultPrice !== null) ? numberWithCommas(Math.ceil(dish.defaultPrice)) : '' }}đ /
                  {{ (dish.dishUnit !== null) ? dish.dishUnit : '' }}
                </template>
                <template v-else>- -</template>

              </td>
              <td>
                <div class="table__option table__option-inline">
                  <router-link tag="button" class="btn-default-green btn-xs btn-yellow table__option--link"
                               :to="{ name: 'backend-dish-edit', params: { id: dish.dishId } }">
                    <i class="fas fa-edit"></i>
                  </router-link>
                  <button @click="_handleDeleteSelected(dish)"
                          class="btn-default-green btn-xs btn-red table__option--delete">
                    <i class="fas fa-trash-alt"></i>
                  </button>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </template>
        <div v-else class="text-center">
          Danh sách trống
        </div>
        <div v-if="totalPages > 0"
             class="list__pagging">
          <button v-for="(item, key) in totalPages" :key="key"
                  @click="_handlePaggingButton(key + 1)"
                  :class="['pagging-item', (key + 1 === searchForm.page) ? 'active' : '']">
            {{ key + 1 }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {convert_code, isLostConnect} from "../../../static";

export default {
  data() {
    return {
      dishes: null,
      categories: null,
      categoryIndex: 0,
      dishSearch: {
        default: '',
        converted: ''
      },
      pageIndex: 1,
      totalPages: 0,
      isSelectedAll: false,
      searchForm: {
        id: '',
        name: '',
        page: 1
      }
    };
  },
  created() {
    this.initCategories();
  },
  methods: {
    _handleDishSearchChange() {
      this.dishSearch.converted = convert_code(this.dishSearch.default);
    },
    initCategories() {
      this.$store.dispatch('openLoader');
      this.$store.dispatch('getAllCategories')
        .then(({data}) => {
          this.categories = data;
          this.searchDish();
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    searchDish(isSelectAll = false) {
      this.$store.dispatch('openLoader')
      this.$store.dispatch('searchALlDishes', this.searchForm)
        .then(({data}) => {
          this.isSelectedAll = isSelectAll;
          if (data && data.result && data.result.length > 0) {
            data.result.map(item => {
              item['isSelected'] = this.isSelectedAll;
              return item;
            });
          }
          this.dishes = data.result;
          this.totalPages = data.totalPages;
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    numberWithCommas(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    checkRightCategory(categories) {
      if (this.categoryIndex === 0) return true;
      if (categories.length === 0) return false;
      let output = false;
      categories.forEach(category => {
        if (category.categoryId === this.categoryIndex) output = true;
      });
      return output;
    },
    _handleSelectAll() {
      if (this.dishes && this.dishes.length > 0) {
        this.dishes.map(dish => {
          dish.isSelected = this.isSelectedAll;
          return dish;
        })
      }
    },
    _handleSelectItem(key) {
      this.dishes[key].isSelected = !this.dishes[key].isSelected;
      this.isSelectedAll = false;

    },
    _handleButtonSearch() {
      this.searchForm = {
        id: (this.categoryIndex === 0) ? '' : this.categoryIndex,
        name: this.dishSearch.converted,
        page: 1
      };
      this.searchDish();
    },
    _handlePaggingButton(index) {
      this.searchForm.page = index;
      this.searchDish(this.isSelectedAll);
    },
    _handleDeleteSelected(dish) {
      let listData = [];
      listData.push(dish.dishId);
      this.confirmDelete(listData);
    },
    _handleDeleteMany() {
      let listData = [];
      this.dishes.forEach(dish => {
        if (dish.isSelected) {
          listData.push(dish.dishId);
        }
      });
      if (listData.length > 0) {
        this.confirmDelete(listData);
      } else {
        this.$swal({
          position: 'top-end',
          icon: 'warning',
          title: 'Chưa chọn thực đơn để xoá',
          showConfirmButton: false,
          timer: 5000,
          toast: true,
        })
      }
    },
    confirmDelete(listData) {
      this.$swal({
        title: `Xoá ${(listData.length > 1) ? listData.length : ''} thực đơn?`,
        html: 'Bạn có chắc chắn muốn xoá.',
        icon: 'warning',
        confirmButtonText: 'Xoá',
        confirmButtonColor: '#F05348',
        showCancelButton: true,
        cancelButtonText: 'Huỷ',
        showCloseButton: true
      }).then((result) => {
        if (result.value) {
          this.$store.dispatch('openLoader');
          this.$store.dispatch('deleteDishById', listData)
            .then(response => {
              this.$swal({
                position: 'top-end',
                icon: 'success',
                title: 'Xoá thực đơn thành công',
                showConfirmButton: false,
                timer: 5000,
                toast: true,
              })
              this.searchForm = {
                id: '',
                name: '',
                page: 1
              }
              this.searchDish();
            }).catch(error => {
            if (!isLostConnect(error, false)) {
              this.$swal({
                title: 'Có lỗi xảy ra',
                html: 'Vui lòng thử lại',
                icon: 'warning',
                showCloseButton: true,
                confirmButtonText: 'Đóng',
              });
            }
          }).finally(() => {
            this.$store.dispatch('closeLoader');
          })
        }
      })
    }
  }
}
</script>
