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
        <div class="be-select__item">
          <label>Khoảng thời gian</label>
          <select @change="_handleSelectFromChange"
                  v-model="searchForm.selectFrom" class="select__type">
            <option :value="0">
              Ngày tự chọn
            </option>
            <option :value="1">
              Trong ngày
            </option>
            <option :value="2" selected>
              Trong tuần
            </option>
            <option :value="3">
              Trong tháng
            </option>
            <option :value="4">
              Trong năm
            </option>
          </select>
        </div>
        <div class="be-select__item">
          <label>Từ</label>
          <date-picker
            v-model="searchForm.dateFrom"
            :editable="false"
            :clearable="false"
            value-type="YYYY-MM-DD"
            :confirm="true"
            @open="_handleDateFromOpenPanel"
            @pick="_handleDateFromChange"
            @confirm="_handleDateFromChange"
            title-format="format">
          </date-picker>
        </div>
        <div class="be-select__item">
          <label>Đến</label>
          <date-picker
            v-model="searchForm.dateTo"
            :editable="false"
            :clearable="false"
            value-type="YYYY-MM-DD"
            :confirm="true"
            @open="_handleDateToOpenPanel"
            @pick="_handleDateToChange"
            @confirm="_handleDateToChange"
            title-format="format">
          </date-picker>
        </div>
        <div class="be-select__item">
          <label>Nhà cung cấp</label>
          <select v-if="suppliers !== null" v-model="searchForm.id"
                  :defaultValue="''" class="select__type">
            <option :value="''">
              Tất cả nhà cung cấp
            </option>
            <option v-for="(supplier, key) in suppliers" :key="key"
                    :value="supplier.supplierId">
              {{ (supplier.supplierName !== null) ? supplier.supplierName : '' }}
            </option>
            <option :value="0">
              Không có nhà cung cấp
            </option>
          </select>
        </div>
        <div class="be-select__item">
          <label></label>
          <button @click="_handleButtonSearchClick" class="select__search btn-default-green">
            Tìm kiếm
          </button>
        </div>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-file-alt"></i>
        Lịch sử nhập kho
      </div>
      <div class="list-body">
        <div class="list__option">
          <button @click="_handleRefreshButtonClick" class="btn-default-green">
            Làm mới
          </button>
        </div>
        <table v-if="importReports && importReports.length > 0" class="list__table">
          <thead>
          <tr>
            <th> STT</th>
            <th> Mã phiếu</th>
            <th> Nhà cung cấp</th>
            <th> Ngày nhập</th>
            <th> Tổng tiền</th>
            <th> Ghi chú</th>
            <th> Tên NVL</th>
            <th>Lựa chọn</th>
          </tr>
          </thead>
          <template v-if="importReports !== null">
            <tbody v-for="(report, key) in importReports" :key="key">
            <tr v-for="(materialReport, mKey) in report.importMaterials" :key="mKey">
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ key + 1 }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.importCode !== null) ? report.importCode : '' }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.supplier !== null && report.supplier.supplierName !== null) ? report.supplier.supplierName :
                '- -' }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.createdDate !== null) ? report.createdDate : '' }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.totalAmount !== null) ? `${number_with_commas(report.totalAmount)}đ` : '' }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.comment !== null) ? report.comment : '' }}
              </td>
              <td>
                {{ (materialReport.material !== null && materialReport.material.materialName !== null) ?
                materialReport.material.materialName : '' }}
              </td>
              <td>
                <div class="table__option table__option-inline">
                  <button @click="_handleImportMaterialDetail(materialReport.importMaterialId)" class="btn-default-green btn-xs table__option--delete">
                    Chi tiết
                  </button>
                </div>
              </td>
            </tr>
            </tbody>
          </template>
        </table>
        <div v-else class="text-center">
          Danh sách trống
        </div>
        <div v-if="totalPages > 0"
             class="list__pagging">
          <button v-for="(item, key) in totalPages" :key="key"
                  @click="_handlePaggingButton(key + 1)"
                  :class="['pagging-item', (key + 1 === searchForm.page) ? 'active' : '']">
            {{key + 1}}
          </button>
        </div>
      </div>
    </div>
    <BackendInventoryImportAddNew :_eventAfterAddnew="_eventAfterAddnew"/>
    <BackendInventoryImportMaterialDetail :importMaterialDetail="importMaterialDetail"/>
  </div>
</template>

<script>
  import BackendInventoryImportAddNew from "./BackendInventoryImportAddNew";
  import BackendInventoryImportMaterialDetail from "./BackendInventoryImportMaterialDetail";
  import {
    isLostConnect,
    check_null,
    number_with_commas,
    check_min_date
  } from "../../../../static";


  export default {
    data() {
      return {
        importReports: null,
        suppliers: null,
        importMaterialDetail: null,
        date: {
          dateFrom: new Date().toISOString().slice(0, 10),
          dateTo: new Date().toISOString().slice(0, 10),
        },
        searchForm: {
          selectFrom: 2,
          dateFromTerm: null,
          dateFrom: null,
          dateToTerm: null,
          dateTo: null,
          id: '',
          page: 1
        },
        totalPages: null,
      };
    },
    components: {
      BackendInventoryImportAddNew,
      BackendInventoryImportMaterialDetail
    },
    created() {
      this.initSuppliers();
    },
    methods: {
      number_with_commas,
      initSuppliers() {
        this.$store.dispatch('openLoader')
        this.$store.dispatch('getAllSupplier')
          .then( async ({data}) => {
            this.suppliers = data
            await this._handleSelectFromChange();
            await this.initSearchForm();
            await this.searchImport();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        }).finally(() => {
          this.$store.dispatch('closeLoader');
        })
      },
      initSearchForm() {
        console.log(this.searchForm)
        if (localStorage) {
          let searchFrom = localStorage.getItem('inventory-import-select-from');
          let from = localStorage.getItem('inventory-import-from');
          let to = localStorage.getItem('inventory-import-to');
          if (!check_null(searchFrom) && searchFrom !== '') this.searchForm.selectFrom = parseFloat(searchFrom);
          if (!check_null(from) && searchFrom !== '') this.searchForm.dateFrom = from;
          if (!check_null(to) && searchFrom !== '') this.searchForm.dateTo = to;
          console.log(this.searchForm)
        }
      },
      async _eventAfterAddnew() {
        this.searchForm.selectFrom = 2;
        await this._handleSelectFromChange();
        await this._handleButtonSearchClick();
      },
      searchImport() {
        let searchRequest = {
          id: (this.searchForm.id >= 0) ? this.searchForm.id : '',
          dateFrom: (!check_null(this.searchForm.dateFrom) && (this.searchForm.dateFrom !== 'null')) ? this.searchForm.dateFrom : '',
          dateTo: (!check_null(this.searchForm.dateTo) && (this.searchForm.dateTo !== 'null')) ? this.searchForm.dateTo : '',
          page: (this.searchForm.page > 0) ? this.searchForm.page : 1,
        }
        this.$store.dispatch('openLoader')
        this.$store.dispatch('searchAllImport', searchRequest)
          .then(({data}) => {
            this.totalPages = data.totalPages;
            this.importReports = data.result;
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        }).finally(() => {
          this.$store.dispatch('closeLoader');
        })
      },
      _handleSelectFromChange() {
        var curr = new Date;
        let first;
        let last;
        switch (this.searchForm.selectFrom) {
          case 0:
            break;
          case 1:
            first = new Date(curr.getFullYear(), curr.getMonth(), curr.getDate());
            last = new Date(curr.getFullYear(), curr.getMonth(), curr.getDate());
            break;
          case 2:
            first = new Date(curr.getFullYear(), curr.getMonth(), curr.getDate() - (curr.getDay() === 0 ? 7 : curr.getDay()) + 1);
            last = new Date(first.getFullYear(), first.getMonth(), first.getDate() + 6);
            break;
          case 3:
            first = new Date(curr.getFullYear(), curr.getMonth(), 1);
            last = new Date(first.getFullYear(), first.getMonth() + 1, 0);
            break;
          case 4:
            first = new Date(curr.getFullYear(), 0, 1);
            last = new Date(first.getFullYear() + 1, 0, 0);
            break;
        }
        this.searchForm.dateFrom = `${first.getFullYear()}-${(first.getMonth() < 10) ? `0${first.getMonth() + 1}` : first.getMonth() + 1}-${(first.getDate() < 10) ? `0${first.getDate()}` : first.getDate()}`;
        this.searchForm.dateTo = `${last.getFullYear()}-${(last.getMonth() < 10) ? `0${last.getMonth() + 1}` : last.getMonth() + 1}-${(last.getDate() < 10) ? `0${last.getDate()}` : last.getDate()}`;
      },
      _handleDateFromOpenPanel() {
        this.searchForm.dateFromTerm = this.searchForm.dateFrom;
      },
      _handleDateFromChange(dateInput) {
        if (check_min_date(dateInput)) {
          this.searchForm.selectFrom = 0;
        } else {
          this.searchForm.dateFrom = this.searchForm.dateFromTerm;
        }
      },
      _handleDateToOpenPanel() {
        this.searchForm.dateToTerm = this.searchForm.dateTo;
      },
      _handleDateToChange(dateInput) {
        if (check_min_date(dateInput)) {
          this.searchForm.selectFrom = 0;
        } else {
          this.searchForm.dateTo = this.searchForm.dateToTerm;
        }
      },
      _handleRefreshButtonClick() {
        this.searchForm.page = 1;
        this.searchImport();
      },
      _handleButtonSearchClick() {
        this.searchForm.page = 1;
        if (localStorage) {
          localStorage.setItem('inventory-import-select-from', this.searchForm.selectFrom.toString());
          localStorage.setItem('inventory-import-from', this.searchForm.dateFrom);
          localStorage.setItem('inventory-import-to', this.searchForm.dateTo);
          let searchFrom = localStorage.getItem('inventory-import-select-from');
          let from = localStorage.getItem('inventory-import-from');
          let to = localStorage.getItem('inventory-import-to');
        }
        this.searchImport();
      },
      _handlePaggingButton(index) {
        this.searchForm.page = index;
        this.searchImport();
      },
      _handleImportMaterialDetail(id) {
        this.$store.dispatch('openLoader')
        this.$store.dispatch('getImportMaterialDetail', id)
          .then(({data}) => {
            this.importMaterialDetail = data;
            this.$bvModal.show('inventory_material-detail');
          }).catch(error => {
          if (!isLostConnect(error, false)) {

          }
        }).finally(() => {
          this.$store.dispatch('closeLoader');
        })
      }
    }
  }
</script>
