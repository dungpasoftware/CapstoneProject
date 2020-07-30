<template>
  <div>
    <div class="be-select">
      <div class="be-select--left">
        <input type="text" class="select__name"
               v-model="materialNameIndex"
               placeholder="Tên nguyên vật liệu"/>
        <select v-model="groupMaterialIndex" class="select__type">
          <option :value="0">Tất cả nhóm nguyên vật liệu</option>
          <option v-for="(group, key) in groupMaterials" :value="group.groupId" :key="key">
            {{ (group.groupName !== null) ? group.groupName : '' }}
          </option>
        </select>
        <button class="select__search btn-default-green" @click="_handleButtonSearch">
          Tìm kiếm
        </button>
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
          <button @click="_handleButtonSearch" class="btn-default-green">
            Làm mới
          </button>
        </div>
        <table class="list__table">
          <thead>
          <tr>
            <th> STT</th>
            <th> Mã NVL</th>
            <th> Tên NVL</th>
            <th> Nhóm</th>
            <th> Đơn vị</th>
            <th> Số lượng nhập</th>
            <th> Lượng hàng đã xuất</th>
            <th> Còn lại</th>
            <th></th>
          </tr>
          </thead>
          <tbody v-if="materials !== null && materials.length > 0">
          <tr v-for="(material, key) in materials" :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              {{ (material.materialCode !== null) ? material.materialCode : '' }}
            </td>
            <td>
              {{ (material.materialName !== null) ? material.materialName : '' }}
            </td>
            <td>
              {{ (material.groupMaterial !== null && material.groupMaterial.groupName !== null) ?
              material.groupMaterial.groupName : '' }}
            </td>
            <td>
              {{ (material.unit !== null) ? material.unit : '' }}
            </td>
            <td>
              {{ (material.unitPrice !== null) ? material.unitPrice : '' }}
            </td>
            <td>
              {{ (material.remain !== null) ? material.remain : '' }}
            </td>
            <td>
              {{ material.remainNotification }}
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleEditClick(material.materialId)"
                        class="btn-default-green btn-xs btn-yellow table__option--link">
                  Cập nhật
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
                  :class="['pagging-item', (key + 1 === materialSearch.page) ? 'active' : '']">
            {{key + 1}}
          </button>
        </div>
      </div>
    </div>
    <BackendInventoryAddNew :initInventory="initInventory"/>
    <BackendInventoryEdit :materialEditData="materialEditData" :initInventory="initInventory"/>
  </div>
</template>

<script>
  import * as staticFunction from '../../../static'
  import BackendInventoryAddNew from "./BackendInventoryAddNew";
  import BackendInventoryEdit from "./BackendInventoryEdit";
  import {xoa_dau} from "../../../static";


  export default {
    data() {
      return {
        materials: null,
        groupMaterials: null,
        materialNameIndex: '',
        groupMaterialIndex: 0,
        materialEditData: null,
        materialSearch: {
          id: '',
          name: '',
          page: 1
        },
        pageIndex: 1,
        totalPages: 0,
      };
    },
    components: {
      BackendInventoryAddNew,
      BackendInventoryEdit
    },
    created() {
      this.initInventory();
      this.initGroupInventory();
    },
    methods: {
      initInventory() {
        this.materialSearch = {
          id: '',
          name: '',
          page: 1
        };
        this.searchAllMaterial()
      },
      searchAllMaterial() {
        this.$store.dispatch('searchAllMaterial', this.materialSearch)
          .then(({data}) => {
            this.materials = data.result;
            this.totalPages = data.totalPages
          })
      },
      initGroupInventory() {
        this.$store.dispatch('getAllGroupMaterial')
          .then(response => {
            console.log(response.data)
            this.groupMaterials = response.data;
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
      getMaterialGroupName(material) {
        return material.groupMaterial.groupName;
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
      },
      _handleButtonSearch() {
        this.materialSearch = {
          id: (this.groupMaterialIndex > 0) ? this.groupMaterialIndex : '',
          name: xoa_dau(this.materialNameIndex).toUpperCase().trim().replace(/\s+/g, ''),
          page: 1
        };
        this.searchAllMaterial()
      },
      _handlePaggingButton(pageIndex) {
        this.materialSearch.page = pageIndex;
        this.searchAllMaterial();
      },
      _handleEditClick(materialId) {
        this.$store.dispatch('getMaterialById', materialId)
          .then(response => {
            this.materialEditData = response.data;
            this.materialEditData.groupMaterial =
              (this.materialEditData.groupMaterial !== null) ?
                this.materialEditData.groupMaterial :
                {
                  groupId: null,
                  groupName: null
                }
            this.$bvModal.show('inventory_edit');
          })
      }
    }
  }
</script>
