<template>
  <div>
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
        Danh sách đơn hàng
      </div>
      <div class="list-body">
        <table class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Mã hoá đơn
            </th>
            <th>
              Bàn
            </th>
            <th>
              Nhân viên order
            </th>
            <th>
              Ngày
            </th>
            <th>
              Thời gian phục vụ
            </th>
            <th>
              Tổng tiền
            </th>
            <th>
              Khách đã trả
            </th>
            <th>
              Ghi chú
            </th>
            <th>
              Chi tiết
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
              Bàn 2
            </td>
            <td>
              NV1
            </td>
            <td>
              31-02-2020 06:30:00 PM
            </td>
            <td>
              1 giờ 30 phút
            </td>
            <td>
              100.000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              Comment
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleOpenBill"
                  class="btn-default-green btn-xs table__option--delete">
                  Xem hoá đơn
                </button>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <span class="add-new" ><i class="fad fa-plus-circle"></i></span>
            </td>
            <td :colspan="13"></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <BackendReportOrderBill />
  </div>
</template>

<script>
  import * as staticFunction from '../../../../static'
  import BackendReportOrderBill from "./BackendReportOrderBill";

  export default {
    components: {
      BackendReportOrderBill
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
