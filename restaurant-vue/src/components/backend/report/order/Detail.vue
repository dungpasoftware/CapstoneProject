<template>
  <b-modal id="report_order" @hidden="_handleCloseModal" size="xl" hide-footer
           hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fal fa-file-alt"></i>
        <template v-if="true">
          Phở bò
        </template>
      </div>
      <div class="modal-head__close" @click="_handleCloseModal">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-report">
      <div class="modal-report__top">
        <div class="top-item-free">
          <h6 style="text-transform: uppercase">
            <strong>Danh sách chi tiết</strong>
          </h6>
        </div>
      </div>
      <div class="modal-report__body mt-0" style="max-height: 50vh; overflow-y: auto">
        <table class="body-table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Ngày
            </th>
            <th>
              Giờ
            </th>
            <th>
              Loại (nhập/xuất)
            </th>
            <th>
              Mã phiếu
            </th>
            <th>
              Nhà cung cấp
            </th>
            <th>
              Kho
            </th>
            <th>
              Số lượng
            </th>
            <th>
              Đơn giá
            </th>
            <th>
              Số tiền
            </th>
          </tr>
          </thead>
            <tbody>
              <tr>
                <td></td>
              </tr>
            </tbody>
        </table>
      </div>
      <div class="modal-report__top">
        <div class="top-item-free">
          <h6 style="text-transform: uppercase" class="color-red">
            <strong>Danh sách các lần huỷ</strong>
          </h6>
        </div>
      </div>
      <div class="modal-report__body mt-0" style="max-height: 50vh; overflow-y: auto;">
        <table class="body-table" style="width: auto;">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Số lượng
            </th>
            <th>
              Lý do
            </th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </b-modal>
</template>

<script>

  import {
    check_null, number_with_commas,
  } from "../../../../static";

  export default {
    name: 'BackendReportOrderDetail',
    data() {
      return {
      }
    },
    props: [],
    created() {
    },
    methods: {
      check_null,
      number_with_commas,
      convertData(data) {
        let termDate = null;
        let dataDate = [];
        let dataMaterialByDate = null;
        let listStt = 1;
        if (data !== null && data.length > 0) {
          data.map(detail => {
            let thisDate = detail.createdDate.slice(0, 10);
            let isNextItem;
            if (termDate === null) {
              termDate = thisDate;
              isNextItem = 0;
            } else {
              isNextItem = !(termDate === thisDate) ? 1 : 2;
              termDate = thisDate;
            }
            let materialFix = {...detail};
            materialFix['stt'] = listStt++;
            materialFix['createdTime'] = materialFix.createdDate.slice(11, materialFix.createdDate.length)
            if (isNextItem === 0) {
              termDate = thisDate;
              dataMaterialByDate = {
                createdDate: termDate,
                materialReports: []
              };
              dataMaterialByDate.materialReports.push(materialFix)
            } else if (isNextItem === 1) {
              dataDate.push(dataMaterialByDate)
              termDate = thisDate;
              dataMaterialByDate = {
                createdDate: termDate,
                materialReports: []
              };
              dataMaterialByDate.materialReports.push(materialFix)
            } else {
              dataMaterialByDate.materialReports.push(materialFix)
            }
          });
          dataDate.push(dataMaterialByDate);
        }
        this.dataShow.listReportConvert = dataDate;
      },
      _handleCloseModal() {
        this.$bvModal.hide('report_order');
      },
    }
  }
</script>
