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
        <input type="date"  class="select__name"/>
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
        <table class="list__table">
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
          <tr>
            <td>
              1
            </td>
            <td>
              JFHKEHF46374
            </td>
            <td>
              01-01-2020
            </td>
            <td>
              Thịt bò
            </td>
            <td>
              Đồ ăn sáng
            </td>
            <td>
              Kg
            </td>
            <td>
              100
            </td>
            <td>
              98
            </td>
            <td>
              10
            </td>
            <td>
              abccddss
            </td>
            <td>
              Đang xử lý
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <BackendReportInventoryAddnew />
  </div>
</template>

<script>
  import * as staticFunction from '../../../../static'
  import BackendReportInventoryAddnew from './BackendReportInventoryAddnew'

  export default {
    components: {
      BackendReportInventoryAddnew
    },
    data() {
      return {
        options: null,
        optionIndex: 0,
      };
    },
    created() {
    },
    methods: {
      _handleOpenBill() {
        this.$bvModal.show('report_order_bill')
      }
    }
  }
</script>
