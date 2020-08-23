<template>
  <b-modal id="inventory_report_detail" @hidden="eventCloseModal" @shown="initSuppliers" size="xl" hide-footer
           hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fal fa-file-alt"></i>
        <template v-if="materialDetail !== null">
          {{ (materialDetail.materialName !== null) ? materialDetail.materialName : '' }}
        </template>
      </div>
      <div class="modal-head__close" @click="_handleCloseModal">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-report">
      <div class="modal-report__top" v-if="materialDetail !== null">
        <div class="top-item-free">
          Đơn vị:
          <span class="top-item__value">
              {{ (materialDetail.unit !== null) ? materialDetail.unit : '' }}
          </span>
        </div>
        <div class="top-item-free">
          Nhóm:
          <span class="top-item__value">
              {{
              (materialDetail.groupMaterial !== null && materialDetail.groupMaterial.groupName !== null) ?
                materialDetail.groupMaterial.groupName : ''
            }}
          </span>
        </div>
        <div class="top-item-free">
          Tổng nhập:
          <span class="top-item__value">
              {{ (materialDetail.totalImport !== null) ? number_with_commas(materialDetail.totalImport) : '' }}
          </span>
        </div>
        <div class="top-item-free">
          Tổng xuất:
          <span class="top-item__value">
              {{ (materialDetail.totalExport !== null) ? number_with_commas(materialDetail.totalExport) : '' }}
          </span>
        </div>
        <div class="top-item-free">
          Còn lại:
          <span class=" top-item__value color-red" v-if="materialDetail.remain <= materialDetail.remainNotification"
                v-b-popover.hover.bottom="`Số lượng tối thiểu là ${number_with_commas(material.remainNotification)}`">
                <i class="fad fa-engine-warning"></i>
                {{
              (materialDetail.remain !== null && materialDetail.remainNotification !== null) ?
                number_with_commas(materialDetail.remain)
                : 0
            }}
              </span>
          <span class="top-item__value" v-else>
                {{
              (materialDetail.remain !== null && materialDetail.remainNotification !== null) ?
                number_with_commas(materialDetail.remain)
                : 0
            }}
              </span>
        </div>
      </div>
      <div class="modal-report__top" v-if="materialDetail !== null">
        <div class="top-item-free">
          Mã phiếu:
          <input :maxlength="100" class="top-item-free__input" type="text" v-model="searchForm.code"
                 @input="_handleSearchChange">
        </div>
        <div class="top-item-free">
          Từ:
          <date-picker
            v-model="searchForm.from"
            :editable="false"
            :clearable="false"
            value-type="YYYY-MM-DD"
            :confirm="true"
            @open="_handleFromOpenPanel"
            @pick="_handleFromChange"
            @confirm="_handleFromChange"
            title-format="format">
          </date-picker>
        </div>
        <div class="top-item-free">
          Đến:
          <date-picker
            v-model="searchForm.to"
            :editable="false"
            :clearable="false"
            value-type="YYYY-MM-DD"
            :confirm="true"
            @open="_handleToOpenPanel"
            @pick="_handleToChange"
            @confirm="_handleToChange"
            title-format="format">
          </date-picker>
        </div>
        <div class="top-item-free" v-if="suppliers !== null && suppliers.length > 0">
          Nhà cung cấp:
          <select class="top-item-free__input" v-model="searchForm.supplier" @change="_handleSearchChange">
            <option :value="null">Tất cả</option>
            <option :value="s.supplierName" v-for="(s, key) in suppliers">
              {{ s.supplierName }}
            </option>
          </select>
        </div>
        <div class="top-item-free" v-if="suppliers !== null && suppliers.length > 0">
          Loại:
          <select class="top-item-free__input" :defaultValue="null" v-model="searchForm.type"
                  @change="_handleSearchChange">
            <option :value="null">Cả nhập và xuất</option>
            <option :value="'import'">Chỉ nhập</option>
            <option :value="'export'">Chỉ xuất</option>
          </select>
        </div>

      </div>
      <div v-if="dataShow.listReportConvert && dataShow.listReportConvert.length > 0" class="modal-report__body">
        <table v-if="materialReportDetail !== null && materialReportDetail.length > 0" class="body-table">
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
          <template v-if="dataShow.listReportConvert !== null && dataShow.listReportConvert.length > 0">
            <tbody v-for="(listDate, key) in dataShow.listReportConvert" :key="key">
            <template v-if="listDate.materialReports !== null && listDate.materialReports.length > 0">
              <tr v-for="(material, mkey) in listDate.materialReports" :key="mkey">
                <td>
                  {{ !check_null(material.stt) ? material.stt : 0 }}
                </td>
                <td v-if="mkey === 0" :rowspan="listDate.materialReports.length">
                  <strong>{{ !check_null(listDate.createdDate) ? listDate.createdDate : '- -' }}</strong>
                </td>
                <td>
                  {{ !check_null(material.createdTime) ? material.createdTime : '- -' }}
                </td>
                <td>
                  <template v-if="!check_null(material.type)">
                    <template v-if="material.type === 'import'">
                      <i class="fad fa-file-import"></i>
                      Nhập
                    </template>
                    <template v-else>
                      <i class="fad fa-file-export"></i>
                      Xuất
                    </template>
                  </template>
                </td>
                <td>
                  {{ !check_null(material.code) ? material.code : '- -' }}
                </td>
                <td>
                  {{ !check_null(material.supplierName) ? material.supplierName : '- -' }}
                </td>
                <td>
                  {{ !check_null(material.warehouseName) ? material.warehouseName : '- -' }}
                </td>
                <td>
                  {{ !check_null(material.quantity) ? number_with_commas(material.quantity) : 0 }}
                  {{ (materialDetail.unit !== null) ? materialDetail.unit : '' }}
                </td>
                <td>
                  {{ !check_null(material.unitPrice) ? number_with_commas(material.unitPrice) : 0 }}đ
                </td>
                <td>
                  {{ !check_null(material.totalAmount) ? number_with_commas(material.totalAmount) : 0 }}đ
                </td>
              </tr>
            </template>
            </tbody>
          </template>
        </table>
      </div>
      <div v-else class="text-center mt-4">
        Danh sách trống
      </div>
      <div class="modal-report__sum">
        <div v-if="sumQuantity.import.isHave || sumQuantity.export.sum" class="modal-report__sum-item">
          <strong>Theo dữ liệu tìm kiếm</strong>
        </div>
        <div v-if="sumQuantity.import.isHave" class="modal-report__sum-item">
          <i class="fad fa-file-import"></i>
          Tổng nhập:
          <strong>{{ sumQuantity.import.sum }}</strong>
        </div>
        <div v-if="sumQuantity.export.isHave" class="modal-report__sum-item">
          <i class="fad fa-file-export"></i>
          Tổng xuất:
          <strong>{{ sumQuantity.export.sum }}</strong>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>

import {
  check_null, number_with_commas, insertCommasDecimal, isLostConnect, check_min_date
} from "../../../static";

export default {
  name: 'BackendInventoryImportMaterialDetail',
  data() {
    return {
      suppliers: null,
      materialDetail: null,
      materialReportDetail: null,
      dataShow: {
        listReportConvert: [],
        listReportSearch: []
      },
      searchForm: {
        code: null,
        from: null,
        fromTerm: null,
        to: null,
        toTerm: null,
        supplier: null,
        type: null
      },
      sumQuantity: {
        import: {
          sum: 0,
          isHave: false
        },
        export: {
          sum: 0,
          isHave: false
        }
      }
    }
  },
  props: ['materialReport', 'material'],
  created() {
    this.initSuppliers();
  },
  methods: {
    check_null,
    number_with_commas,
    insertCommasDecimal,
    _handleFromOpenPanel() {
      this.searchForm.fromTerm = this.searchForm.from;
    },
    _handleFromChange(dateInput) {
      if (check_min_date(dateInput)) {
        this._handleSearchChange();
      } else {
        this.searchForm.from = this.searchForm.fromTerm;
      }
    },
    _handleToOpenPanel() {
      this.searchForm.toTerm = this.searchForm.to;
    },
    _handleToChange(dateInput) {
      if (check_min_date(dateInput)) {
        this._handleSearchChange();
      } else {
        this.searchForm.to = this.searchForm.toTerm;
      }
    },
    initSuppliers() {
      this.$store.dispatch('openLoader');
      this.$store.dispatch('getAllSupplier')
        .then(({data}) => {
          this.suppliers = data;
          this.getMaterialData();
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    convertData(data) {
      let termDate = null;
      let dataDate = [];
      let dataMaterialByDate = null;
      let listStt = 1;
      this.sumQuantity = {
        import: {
          sum: 0,
          isHave: false
        },
        export: {
          sum: 0,
          isHave: false
        }
      };
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
          if (materialFix.type === 'import') {
            this.sumQuantity.import.isHave = true;
            this.sumQuantity.import.sum += materialFix.quantity;
          } else if (materialFix.type === 'export') {
            this.sumQuantity.export.isHave = true;
            this.sumQuantity.export.sum += materialFix.quantity;
          }
        });
        dataDate.push(dataMaterialByDate);
      }
      this.dataShow.listReportConvert = dataDate;
    },
    getMaterialData() {
      this.dataShow.stt = 0;
      this.materialDetail = this.material;
      this.materialReportDetail = this.materialReport;
      this.convertData(this.materialReportDetail);
    },
    eventCloseModal() {
      this.materialDetail = null;
      this.materialReportDetail = null;
      this.searchForm = {
        code: null,
        from: null,
        to: null,
        supplier: null
      }
    },
    _handleCloseModal() {
      this.$bvModal.hide('inventory_report_detail');
    },
    _handleSearchChange() {
      let code = this.searchForm.code;
      let from = null;
      if (this.searchForm.from !== null) {
        from = new Date(Date.UTC(parseInt(this.searchForm.from.slice(0, 4)), parseInt(this.searchForm.from.slice(5, 7)), parseInt(this.searchForm.from.slice(8, 10))));
      }
      let to = null;
      if (this.searchForm.to !== null) {
        to = new Date(Date.UTC(parseInt(this.searchForm.to.slice(0, 4)), parseInt(this.searchForm.to.slice(5, 7)), parseInt(this.searchForm.to.slice(8, 10))));
      }
      let supplier = this.searchForm.supplier;
      let type = this.searchForm.type;

      this.dataShow.listReportSearch = [];
      if (this.materialReportDetail !== null && this.materialReportDetail.length > 0) {
        this.materialReportDetail.map(item => {
          let checked = true;
          let itemDate = (item.createdDate !== null) ?
            new Date(Date.UTC(parseInt(item.createdDate.slice(6, 10)), parseInt(item.createdDate.slice(3, 5)), parseInt(item.createdDate.slice(0, 2))))
            : null;

          if (!item.code.toLowerCase().includes(code.toLowerCase())) {
            checked = false;
          }
          if (from !== null) {
            if (itemDate < from) checked = false;
          }
          if (to !== null) {
            if (itemDate > to) checked = false;
          }
          if (supplier !== null) {
            if (supplier !== item.supplierName) checked = false;
          }
          if (type !== null) {
            if (item.type !== type) checked = false;
          }
          if (checked) this.dataShow.listReportSearch.push(item)
        });
      }
      this.convertData(this.dataShow.listReportSearch)
    }
  }
}
</script>
