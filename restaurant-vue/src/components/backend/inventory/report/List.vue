<template>
  <div>
    <div class="be-select">
    </div>
    <div class="be-select">
      <div class="be-select--left">
        <input type="text" class="select__name" placeholder="Tìm kiếm theo mã kiểm kê" v-model="searchForm.name" @input="_handleInputName"/>
      </div>
      <div class="be-select--right">
        <button v-b-modal="'report_inventory_new'" class="btn-default-green">
          <i class="fas fa-plus"></i>
          Tạo mới kiểm kê
        </button>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Lịch sử kiểm kê
      </div>
      <div class="list-body">
        <table v-if="inventoryMaterialsView && inventoryMaterialsView.length > 0" class="list__table">
          <thead>
          <tr>
            <th rowspan="2">
              STT
            </th>
            <th rowspan="2">
              Mã kiểm kê
            </th>
            <th rowspan="2">
              Ngày
            </th>
            <th rowspan="2">
              NVL
            </th>
            <th rowspan="2">
              Nhóm
            </th>
            <th rowspan="2">
              Đơn vị tính
            </th>
            <th colspan="3">
              Số lượng
            </th>
            <th rowspan="2">
              Nguyên nhân
            </th>
            <th rowspan="2">
              Xử lý
            </th>
          </tr>
          <tr>
            <th>
              Trên hệ thống
            </th>
            <th>
              Kiểm kê
            </th>
            <th>
              Chênh lệch
            </th>
          </tr>
          </thead>
          <tbody>
          <template v-for="(item, key) in inventoryMaterialsView">
            <tr :key="key">
              <td>
                {{ key + 1 }}
              </td>
              <td>
                {{ (item.inventoryCode) ? item.inventoryCode : '- -' }}
              </td>
              <td>
                {{ (item.inventoryDate) ? item.inventoryDate : '- -' }}
              </td>
              <td>
                {{ (item.materialName) ? item.materialName : '- -'}}
              </td>
              <td>
                {{ (item.groupMaterialName) ? item.groupMaterialName : '- -' }}
              </td>
              <td>
                {{ (item.materialUnit) ? item.materialUnit : '- -' }}
              </td>
              <td>
                {{ (item.remainSystem) ? item.remainSystem : 0 }}
              </td>
              <td>
                {{ (item.remainFact) ? item.remainFact : 0 }}
              </td>
              <td>
                {{ (item.quantityDifferent) ? item.quantityDifferent : 0 }}
              </td>
              <td>
                {{ (item.reason) ? item.reason : '- -' }}
              </td>
              <td>
                {{ (item.process) ? (item.process === 1 ? 'Nhập kho' : item.process === 2 ? 'Xuất kho' : 'Chưa xử lý') : '- -' }}
              </td>
            </tr>
          </template>
          </tbody>
        </table>
        <div v-else class="text-center">
          Danh sách trống
        </div>
      </div>
    </div>
    <BackendReportInventoryAddnew :initInventoryMaterial="initInventoryMaterial"/>
  </div>
</template>

<script>
import BackendReportInventoryAddnew from './Add'
import {isLostConnect, convert_code} from "../../../../static";

export default {
  components: {
    BackendReportInventoryAddnew
  },
  data() {
    return {
      inventoryMaterials: null,
      inventoryMaterialsView: [],
      searchForm: {
        name: ''
      }
    };
  },
  created() {
    this.initInventoryMaterial();
  },
  methods: {
    convert_code,
    initInventoryMaterial() {
      this.$store.dispatch('openLoader')
      this.$store.dispatch('getAllInventoryMaterial')
        .then(response => {
          console.log(response.data)
          this.inventoryMaterials = response.data;
          this.searchForm.name = '';
          this._handleInputName();
        }).catch(error => {
          console.log(error)
        if (!isLostConnect(error)) {
        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    _handleOpenBill() {
      this.$bvModal.show('report_order_bill')
    },
    _handleInputName() {
      this.inventoryMaterialsView = [];
      if (this.inventoryMaterials && this.inventoryMaterials.length > 0) {
        this.inventoryMaterials.map(item => {
          if (item.inventoryCode.toLowerCase().includes(this.searchForm.name.toLowerCase())) {
            this.inventoryMaterialsView.push(item);
          }
        })
      }
    }
  }
}
</script>
