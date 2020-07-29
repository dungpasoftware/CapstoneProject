<template>
  <div>
    <div class="be-select">
      <div class="be-select--left">
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
        <button class="select__search btn-default-green" @click="_handleButtonSearch">
          Tìm kiếm
        </button>
      </div>
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
            <th> Còn lại</th>
            <th> Lựa chọn</th>
          </tr>
          </thead>
          <tbody v-if="dishes !== null">
          <tr v-for="(dish, key, index) in dishes"
              :key="key">
            <td>
              <input type="checkbox" v-model="dish.isSelected" @click="_handleSelectItem(key)"/>
            </td>
            <td>
              {{ (dish.dishCode !== null) ? dish.dishCode : '' }}
            </td>
            <td>
              <template v-if="dish.imageUrl !== null">
                <img :src="dish.imageUrl" alt="">
              </template>
            </td>
            <td>
              {{ (dish.dishName !== null) ? dish.dishName : '' }}
            </td>
            <td>
              <div v-if="dish.categories !== null && dish.categories.length > 0"
                   v-for="(category, key) in dish.categories"
                   style="white-space: nowrap">
                {{category.categoryName}}
              </div>
            </td>
            <td>
              {{ (dish.cost !== null) ? numberWithCommas(dish.cost) : '' }}đ
            </td>
            <td>
              {{ (dish.defaultPrice !== null) ? numberWithCommas(dish.defaultPrice) : '' }}đ/{{ (dish.dishUnit !== null)
              ? dish.dishUnit : '' }}
            </td>
            <td>
              {{ (dish.remainQuantity !== null) ? numberWithCommas(dish.remainQuantity) : '' }}
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
        <div v-if="totalPages > 0"
             class="list__pagging">
          <button v-for="(item, key) in totalPages" :key="key"
                  @click="_handlePaggingButton(key + 1)"
                  :class="['pagging-item', (key + 1 === searchForm.page) ? 'active' : '']">
            {{key + 1}}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import * as staticFunction from '../../../static'

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
      this.searchDish();
      this.$store.dispatch('getAllCategories')
        .then(({data}) => {
          this.categories = data;
        }).catch(error => {
        console.log(error)
      })
    },
    methods: {
      _handleDishSearchChange() {
        this.dishSearch.converted = staticFunction.convert_code(this.dishSearch.default);
      },
      searchDish() {
        this.isSelectedAll = false;
        this.$store.dispatch('searchALlDishes', this.searchForm)
          .then(({data}) => {
            data.result.map(item => {
              item['isSelected'] = false;
              return item;
            });
            this.dishes = data.result;
            this.totalPages = data.totalPages;
          }).catch(error => {
          console.log(error)
        });
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
        this.dishes.map(dish => {
          dish.isSelected = this.isSelectedAll;
          return dish;
        })
      },
      _handleSelectItem(key) {
        this.dishes[key].isSelected = !this.dishes[key].isSelected;
        console.log(this.dishes[key]);
        if (this.isSelectedAll) this.isSelectedAll = false;
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
        this.searchDish();
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
        this.confirmDelete(listData);
      },
      confirmDelete(listData) {
        this.$swal(`Xoá thực đơn?`,
          'Bạn có chắc chắn muốn xoá.',
          'warning').then((result) => {
          if (result.value) {
            this.$store.dispatch('deleteDishById', listData)
              .then(response => {
                this.$swal('Thành công!',
                  'Danh sách món ăn đã được cập nhật lên hệ thống.',
                  'success')
                  .then(result => {
                    if (result.value) {
                      this.searchForm = {
                        id: '',
                        name: '',
                        page: 1
                      }
                      this.searchDish();
                    }
                  })
              }).catch(err => {
              console.error(err)
            })
          }
        })
      }
    }
  }
</script>
