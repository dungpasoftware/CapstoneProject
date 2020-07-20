<template>
  <div>
    <div class="be-select">
      <div class="be-select--left">
        <select defaultValue="0" class="select__type">
          <option selected disabled :value="0">Nhóm nguyên vật liệu</option>
            <option value="1">
              Thịt
            </option>
        </select>
        <input type="text" class="select__name"
               placeholder="Tên nguyên vật liệu"/>
      </div>
      <div class="be-select--right">
        <button v-b-modal="'inventory_add_new'" class="btn-default-green">
          Tạo mới tồn kho
        </button>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-dolly-flatbed-alt"></i>
        Danh sách nguyên vật liệu
      </div>
      <div class="list-body">
        <div class="list__option">
          <button class="btn-default-green">
            Làm mới
          </button>
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
            <th> Mã NVL </th>
            <th> Tên NVL </th>
            <th> Đơn vị </th>
            <th> Giá nhập </th>
            <th> Lượng hàng đã xuất </th>
            <th> Lượng hàng tồn </th>
            <th> Lựa chọn </th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              <input type="checkbox" />
            </td>
            <td>
              1
            </td>
            <td>
              Thịt gaf
            </td>
            <td>
              Kg
            </td>
            <td>
              10000
            </td>
            <td>
              20
            </td>
            <td>
              200
            </td>
            <td>
              <div class="table__option">
                <router-link tag="button" class="btn-default-green btn-xs btn-yellow table__option--link"
                             :to="{ name: 'backend-dish-edit'}">
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
    <BackendInventoryAddNew />
  </div>
</template>

<script>
  import * as staticFunction from '../../../static'
  import BackendInventoryAddNew from "./BackendInventoryAddNew";



  export default {
    data() {
      return {
        inventories: null,
        categories: null,
        categoryIndex: 0,
        invenSearch: {
          default: '',
          converted: ''
        },
        isSelectedAll: false
      };
    },
    components: {
      BackendInventoryAddNew
    },
    created() {
    },
    methods: {
      _handleDishSearchChange() {
        this.invenSearch.converted = staticFunction.convert_code(this.invenSearch.default);
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
