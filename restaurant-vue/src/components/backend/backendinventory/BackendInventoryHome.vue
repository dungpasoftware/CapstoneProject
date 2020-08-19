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
          <router-link tag="button" class="btn-default-green btn-blue ml-3" :to="{ name: 'report-inventory' }">
            Kiểm kê
          </router-link>
        </div>
        <table class="list__table" v-if="materials !== null && materials.length > 0">
          <thead>
          <tr>
            <th> STT</th>
            <th> Mã NVL</th>
            <th> Tên NVL</th>
            <th> Nhóm</th>
            <th> Đơn vị</th>
            <th> Giá nhập</th>
            <th> SL nhập </th>
            <th> SL xuất</th>
            <th> Còn lại</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
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
              {{
                (material.groupMaterial !== null && material.groupMaterial.groupName !== null) ?
                  material.groupMaterial.groupName : ''
              }}
            </td>
            <td>
              {{ (material.unit !== null) ? material.unit : '' }}
            </td>
            <td>
              {{ (material.unitPrice !== null) ? number_with_commas(Math.ceil(material.unitPrice)) : 0 }}đ
            </td>
            <td>
              {{ (material.totalImport !== null) ? insertCommasDecimal(material.totalImport) : 0 }}
            </td>
            <td>
              {{ (material.totalExport !== null) ? insertCommasDecimal(material.totalExport) : 0 }}
            </td>
            <td>
              <div class="color-red" v-if="showAlertRemain(material.remain, material.remainNotification)"
                   v-b-popover.hover.bottom="`Số lượng tối thiểu là ${number_with_commas(material.remainNotification)}`">
                <i class="fad fa-engine-warning"></i>
                {{
                  (material.remain !== null && material.remainNotification !== null) ?
                    insertCommasDecimal(material.remain)
                    : 0
                }}
              </div>
              <div v-else>
                {{
                  (material.remain !== null && material.remainNotification !== null) ?
                    number_with_commas(material.remain)
                    : 0
                }}
              </div>
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleShowMaterialReportDetailClick(material.materialId, material)"
                        class="btn-default-green btn-xs table__option--link">
                  Chi tiết
                </button>
                <button @click="_handleEditClick(material.materialId)"
                        class="btn-default-green btn-xs btn-yellow table__option--link">
                  Cập nhật
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div v-else class="text-center">
          Danh sách trống
        </div>
        <div v-if="totalPages > 0"
             class="list__pagging">
          <button v-for="(item, key) in totalPages" :key="key"
                  @click="_handlePaggingButton(key + 1)"
                  :class="['pagging-item', (key + 1 === materialSearch.page) ? 'active' : '']">
            {{ key + 1 }}
          </button>
        </div>
      </div>
    </div>
    <BackendInventoryAddNew :initInventory="initInventory"/>
    <BackendInventoryEdit :materialEditData="materialEditData" :initInventory="initInventory"/>
    <BackendInventoryReportDetail :material="material" :materialReport="materialReportData" />
  </div>
</template>

<script>
import BackendInventoryAddNew from "./BackendInventoryAddNew";
import BackendInventoryEdit from "./BackendInventoryEdit";
import BackendInventoryReportDetail from "./BackendInventoryReportDetail";
import {
  convert_code,
  xoa_dau,
  number_with_commas,
  insertCommasDecimal, isLostConnect
} from "../../../static";


export default {
  data() {
    return {
      material: null,
      materials: null,
      groupMaterials: null,
      materialNameIndex: '',
      groupMaterialIndex: 0,
      materialEditData: null,
      materialReportData: null,
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
    BackendInventoryEdit,
    BackendInventoryReportDetail
  },
  created() {
    this.initGroupInventory();
  },
  methods: {
    insertCommasDecimal,
    initGroupInventory() {
      this.$store.dispatch('getAllGroupMaterial')
        .then(response => {
          this.groupMaterials = response.data;
          this.initInventory();
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      })
    },
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
          console.log(data)
          this.materials = data.result;
          this.totalPages = data.totalPages
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      })
    },
    _handleDishSearchChange() {
      this.invenSearch.converted = convert_code(this.invenSearch.default);
      console.log(this.isSelectedAll)
    },
    number_with_commas,
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
    showAlertRemain(remain, remainNoti) {
      return remain <= remainNoti;
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
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      })
    },
    _handleShowMaterialReportDetailClick(id, materialDetail) {
      this.$store.dispatch('getMaterialReportDetail', id)
        .then(({data}) => {
          this.material = materialDetail;
          this.materialReportData = data;
          this.$bvModal.show('inventory_report_detail');
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      })
    }
  }
}
</script>
