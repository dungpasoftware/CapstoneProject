<template>
  <div>
    <div class="be-select">
      <div class="be-select--left__flex">
        <button v-b-modal="'inventory_import_new'" class="btn-default-green">
          <i class="fad fa-file-import"></i>
          Nhập kho
        </button>
      </div>
    </div>
    <div class="be-select">
      <div class="be-select--left__flex">
        <select defaultValue="0" class="select__type">
            <option value="1">
              Trong ngày
            </option>
        </select>
        <input type="date" class="select__name"/>
        <input type="date" class="select__name"/>
        <select defaultValue="0" class="select__type">
          <option value="1">
            Ngày
          </option>
        </select>
        <select defaultValue="0" class="select__type">
          <option value="1">
            Tất cả
          </option>
        </select>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-file-alt"></i>
        Báo cáo
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
            <th> STT </th>
            <th> Tên NVL </th>
            <th> Mã phiếu </th>
            <th> Nguồn </th>
            <th> Loại phiếu </th>
            <th> Ngày tạo </th>
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
              11103sss
            </td>
            <td>
              0
            </td>
            <td>

            </td>
            <td>
              19/07/2020
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button class="btn-default-green btn-xs table__option--delete">
                  Chi tiết
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <BackendInventoryImportAddNew />
  </div>
</template>

<script>
  import * as staticFunction from '../../../../static'
  import BackendInventoryImportAddNew from "./BackendInventoryImportAddNew";



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
      BackendInventoryImportAddNew
    },
    created() {
      this.initInventory();
    },
    methods: {
      initInventory() {
        this.$store.dispatch('getAllInventory')
          .then(response => {
            console.log(response.data)
          }).catch(err => {
            console.error(err)
        })
      },
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
