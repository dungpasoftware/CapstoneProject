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
        <select @change="_handleSelectFromChange"
                v-model="searchForm.selectFrom" class="select__type">
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
        <input type="date" v-model="searchForm.dateFrom" class="select__name"/>
        <input type="date" v-model="searchForm.dateTo" class="select__name"/>
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
        <button @click="_handleButtonSearchClick" class="select__search btn-default-green">
          Tìm kiếm
        </button>
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
            <th> Tên NVL</th>
            <th></th>
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
                '' }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.createdDate !== null) ? report.createdDate : '' }}
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
          Dữ liệu trống
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
    <BackendInventoryImportAddNew/>
    <BackendInventoryImportMaterialDetail :importMaterialDetail="importMaterialDetail"/>
  </div>
</template>

<script>
  import BackendInventoryImportAddNew from "./BackendInventoryImportAddNew";
  import BackendInventoryImportMaterialDetail from "./BackendInventoryImportMaterialDetail";
  import {
    isLostConnect
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
          manyDay: "",
          dateFrom: null,
          dateTo: null,
          id: '',
          page: 1
        },
        totalPages: null,
        toastError: ''
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
      initSuppliers() {
        this.$store.dispatch('getAllSupplier')
          .then( async ({data}) => {
            this.suppliers = data
            await this._handleSelectFromChange();
            await this.searchImport();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
      },
      searchImport() {
        let params = {
          id: (this.searchForm.id >= 0) ? this.searchForm.id : '',
          dateFrom: this.searchForm.dateFrom,
          dateTo: this.searchForm.dateTo,
          page: (this.searchForm.page > 0) ? this.searchForm.page : 1,
        }
        this.$store.dispatch('searchAllImport', params)
          .then(({data}) => {
            this.totalPages = data.totalPages;
            this.importReports = data.result;
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
      },
      _handleSelectFromChange() {
        var curr = new Date;
        let first;
        let last;
        switch (this.searchForm.selectFrom) {
          case 1:
            first = new Date(Date.UTC(curr.getFullYear(), curr.getMonth(), curr.getDate()));
            last = new Date(Date.UTC(curr.getFullYear(), curr.getMonth(), curr.getDate()));
            break;
          case 2:
            first = new Date(Date.UTC(curr.getFullYear(), curr.getMonth(), curr.getDate() - curr.getDay() + 1));
            last = new Date(Date.UTC(first.getFullYear(), first.getMonth(), first.getDate() + 6));
            break;
          case 3:
            first = new Date(Date.UTC(curr.getFullYear(), curr.getMonth(), 1));
            last = new Date(Date.UTC(first.getFullYear(), first.getMonth() + 1, 0));
            break;
          case 4:
            first = new Date(Date.UTC(curr.getFullYear(), 0, 1));
            last = new Date(Date.UTC(first.getFullYear() + 1, 0, 0));
            break;
        }
        this.searchForm.dateFrom = `${first.getFullYear()}-${(first.getMonth() < 10) ? `0${first.getMonth() + 1}` : first.getMonth() + 1}-${(first.getDate() < 10) ? `0${first.getDate()}` : first.getDate()}`;
        this.searchForm.dateTo = `${last.getFullYear()}-${(last.getMonth() < 10) ? `0${last.getMonth() + 1}` : last.getMonth() + 1}-${(last.getDate() < 10) ? `0${last.getDate()}` : last.getDate()}`;
      },
      _handleRefreshButtonClick() {
        this.searchForm.page = 1;
        this.searchImport();
      },
      _handleButtonSearchClick() {
        let dFrom = new Date(this.searchForm.dateFrom);
        let dTo = new Date(this.searchForm.dateTo);
        if (dTo < dFrom) {
          let append = true;
        }
        this.searchForm.page = 1;
        this.searchImport();
      },
      _handlePaggingButton(index) {
        this.searchForm.page = index;
        this.searchImport();
      },
      _handleImportMaterialDetail(id) {
        this.$store.dispatch('getImportMaterialDetail', id)
          .then(({data}) => {
            this.importMaterialDetail = data;
            this.$bvModal.show('inventory_material-detail');
          }).catch(error => {
          if (!isLostConnect(error, false)) {

          }
        })
      }
    }
  }
</script>
