<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách thực đơn
      </div>
      <div class="list-body">
        <table class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Tên món ăn
            </th>
            <th>
              Tổng số lượng
            </th>
            <th>
              Thời gian
            </th>
            <th>
              Mã hoá đơn
            </th>
            <th>
              Số lượng
            </th>
            <th>
              Số lượng huỷ
            </th>
            <th>
              Giá NVL
            </th>
            <th>
              Giá thành
            </th>
            <th>
              Giá bán
            </th>
            <th>
              Thành tiền
            </th>
            <th>
              Lợi nhuận
            </th>
            <th>
              Trạng thái
            </th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              1
            </td>
            <td rowspan="3">
              <strong>Phở bò</strong>
            </td>
            <td rowspan="3">
              <strong>300</strong>
            </td>
            <td>
              31-02-2020 06:30:00 PM
            </td>
            <td>
              HDHF374234
            </td>
            <td>
              31
            </td>
            <td>
              2
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              statusValue
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleOpenDetail"
                  class="btn-default-green btn-xs table__option--delete">
                  Chi tiết
                </button>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              2
            </td>
            <td>
              31-02-2020 06:30:00 PM
            </td>
            <td>
              HDHF374234
            </td>
            <td>
              31
            </td>
            <td>
              2
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              statusValue
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleOpenDetail"
                  class="btn-default-green btn-xs table__option--delete">
                  Chi tiết
                </button>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              3
            </td>
            <td>
              31-02-2020 06:30:00 PM
            </td>
            <td>
              HDHF374234
            </td>
            <td>
              31
            </td>
            <td>
              2
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              30,000đ
            </td>
            <td>
              statusValue
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleOpenDetail"
                  class="btn-default-green btn-xs table__option--delete">
                  Chi tiết
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
    <BackendReportTopdishDetail />
  </div>
</template>

<script>
  import * as staticFunction from '../../../../static'
  import BackendReportTopdishDetail from "./BackendReportTopdishDetail";

  export default {
    components: {
      BackendReportTopdishDetail
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
      _handleOpenDetail() {
        this.$bvModal.show('report_topdish')
      }
    }
  }
</script>
