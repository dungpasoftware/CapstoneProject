<template>
  <div>
    <div class="be-select">
      <div class="be-select--left">
        <input v-model="dishSearch.default" @input="_handleDishSearchChange()" type="text" class="select__name"
               placeholder="Tên món ăn"/>
        <select v-model="categoryIndex" defaultValue="0" name="" class="select__type">
          <option :value="0">Tất cả</option>
          <template v-if="categories !== null">
            <option v-for="(category, key, index) in categories" :value="category.categoryId" :key="index">
              {{ (category.categoryName !== null) ? category.categoryName : '' }}
            </option>
          </template>
        </select>
        <button class="select__search btn-default-green">
          Sửa nhóm thực đơn
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
          <button class="btn-default-green btn-red">
            Xoá danh sách đã chọn
          </button>
        </div>
        <table class="list__table">
          <thead>
          <tr>
            <th>
              <input type="checkbox" v-model="isSelectedAll" @change="_handleSelectAll"/>
            </th>
            <th> Mã sản phẩm </th>
            <th> Hình đại diện </th>
            <th> Tên món </th>
            <th> Giá nguyên vật liệu </th>
            <th> Giá bán / đơn vị </th>
            <th> Còn lại </th>
            <th> Lựa chọn </th>
          </tr>
          </thead>
          <tbody v-if="dishes !== null">
          <tr v-for="(dish, key, index) in dishes"
              v-if="checkRightCategory(dish.categories) && dish.dishCode.includes(dishSearch.converted)"
              :key="index">
            <td>
              <input type="checkbox" v-model="dish.isSelected" @change="_handleSelectItem(key, !dish.isSelected)"/>
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
              {{ (dish.cost !== null) ? numberWithCommas(dish.cost) : '' }}đ/{{ (dish.dishUnit !== null) ? dish.dishUnit
              : '' }}
            </td>
            <td>
              {{ (dish.defaultPrice !== null) ? numberWithCommas(dish.defaultPrice) : '' }}đ
            </td>
            <td>
              {{ (dish.remainQuantity !== null) ? numberWithCommas(dish.remainQuantity) : '' }}
            </td>
            <td>
              <div class="table__option">
                <router-link tag="button" class="btn-default-green btn-xs btn-yellow table__option--link"
                             :to="{ name: 'backend-dish-edit', params: { id: dish.dishId } }">
                  Chỉnh sửa
                </router-link>
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
        dishes: null,
        categories: null,
        categoryIndex: 0,
        dishSearch: {
          default: '',
          converted: ''
        },
        isSelectedAll: false
      };
    },
    created() {
      this.$store.dispatch('getAllDishes')
        .then(({data}) => {
          data.map(item => {
            item['isSelected'] = false;
            return item;
          });
          this.dishes = data;
        }).catch(error => {
        console.log(error)
      });

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
        console.log(this.isSelectedAll)
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
      _handleSelectItem(key, value) {
        console.log(value)
        this.dishes[key].isSelected = value;
        if (this.isSelectedAll) this.isSelectedAll = false;
      }
    }
  }
</script>
