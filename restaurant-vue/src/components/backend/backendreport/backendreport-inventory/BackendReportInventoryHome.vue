<template>
  <div>
    <div class="be-select">
      <div class="be-select--left__flex">
        <button v-b-modal="'report_inventory_new'" class="btn-default-green">
          <i class="fas fa-plus"></i>
          Tạo mới kiểm kê
        </button>
      </div>
    </div>
    <div class="be-select">
      <div class="be-select--left__flex">
        <select>
          <option :value="1">
            Trong ngày
          </option>
          <option :value="2">
            Trong tuần
          </option>
          <option :value="3">
            Trong tháng
          </option>
          <option :value="4">
            Trong năm
          </option>
        </select>
        <input type="date" class="select__name"/>
        <input type="date" class="select__name"/>
        <select class="select__type">
          <option :value="''">
            Tất cả nhà cung cấp
          </option>
          <option :value="0">
            Không có nhà cung cấp
          </option>
        </select>
        <button class="select__search btn-default-green">
          Tìm kiếm
        </button>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Lịch sử kiểm kê
      </div>
      <div class="list-body">
        <table v-if="inventoryMaterials && inventoryMaterials.length > 0" class="list__table">
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
          <tr v-for="(item, key) in inventoryMaterials" :key="key">
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
              {{ (item.process) ? (item.process < 0 ? 'Nhập kho' : item.process > 0 ? 'Xuất kho' : 'Chưa xử lý') : '- -' }}
            </td>
          </tr>
          </tbody>
        </table>
        <div v-else class="text-center">
          Dữ liệu trống
        </div>
      </div>
    </div>
    <BackendReportInventoryAddnew :initInventoryMaterial="initInventoryMaterial"/>
  </div>
</template>

<script>
import * as staticFunction from '../../../../static'
import BackendReportInventoryAddnew from './BackendReportInventoryAddnew'
import {isLostConnect} from "../../../../static";

export default {
  components: {
    BackendReportInventoryAddnew
  },
  data() {
    return {
      inventoryMaterials: null,
    };
  },
  created() {
    this.initInventoryMaterial();
  },
  methods: {
    initInventoryMaterial() {
      this.$store.dispatch('getAllInventoryMaterial')
        .then(response => {
          this.inventoryMaterials = response.data;
          console.log(this.inventoryMaterials);
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      })
    },
    _handleOpenBill() {
      this.$bvModal.show('report_order_bill')
    }
  }
}
</script>
