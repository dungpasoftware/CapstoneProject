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
        <table class="list__table">
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
  import BackendInventoryImportMaterialDetail from "./BackendInventoryReportDetail";

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
      var curr = new Date;
      let first = curr.getDate() - curr.getDay();
      let last = first + 6;
      this.searchForm.dateFrom = new Date(curr.setDate(first)).toISOString().slice(0, 10);
      if (first > last) {
        this.searchForm.dateTo = new Date(curr.setMonth(curr.getMonth() + 1).setDate(last)).toISOString().slice(0, 10);
      } else {
        this.searchForm.dateTo = new Date(curr.setDate(last)).toISOString().slice(0, 10);
      }
      this.searchImport();
      this.initSuppliers();
    },
    methods: {
      searchImport() {
        let params = {
          id: (this.searchForm.id >= 0) ? this.searchForm.id : '',
          dateFrom: this.searchForm.dateFrom,
          dateTo: this.searchForm.dateTo,
          page: (this.searchForm.page > 0) ? this.searchForm.page : 1,
        }
        console.log(params)
        this.$store.dispatch('searchAllImport', params)
          .then(({data}) => {
            console.log(data)
            this.totalPages = data.totalPages;
            this.importReports = data.result;
          }).catch(err => {
          console.error(err)
        })
      },
      initSuppliers() {
        this.$store.dispatch('getAllSupplier')
          .then(({data}) => {
            console.log(data)
            this.suppliers = data
          }).catch(err => {
          console.error(err)
        })
      },
      _handleSelectFromChange() {
        var curr = new Date;
        let first;
        let last;
        switch (this.searchForm.selectFrom) {
          case 1:
            this.searchForm.dateFrom = curr.toISOString().slice(0, 10);
            this.searchForm.dateTo = curr.toISOString().slice(0, 10);
            break;
          case 2:
            first = curr.getDate() - curr.getDay();
            last = first + 6;
            this.searchForm.dateFrom = new Date(curr.setDate(first)).toISOString().slice(0, 10);
            if (first > last) {
              this.searchForm.dateTo = new Date(curr.setMonth(curr.getMonth() + 1).setDate(last)).toISOString().slice(0, 10);
            } else {
              this.searchForm.dateTo = new Date(curr.setDate(last)).toISOString().slice(0, 10);
            }
            break;
          case 3:
            first = new Date(curr.getFullYear(), curr.getMonth(), 1);
            last = new Date(curr.getFullYear(), curr.getMonth() + 1, 0);
            this.searchForm.dateFrom = `${first.getFullYear()}-${(first.getMonth() < 9) ? `0${first.getMonth() + 1}` : first.getMonth() + 1}-${(first.getDate() < 10) ? `0${first.getDate()}` : first.getDate()}`;
            this.searchForm.dateTo = `${last.getFullYear()}-${(last.getMonth() < 9) ? `0${last.getMonth() + 1}` : last.getMonth() + 1}-${(last.getDate() < 10) ? `0${last.getDate()}` : last.getDate()}`;
            break;
          case 4:
            first = new Date(curr.getFullYear(), 0, 1);
            last = new Date(curr.getFullYear() + 1, 0, 0);
            this.searchForm.dateFrom = `${first.getFullYear()}-${(first.getMonth() < 10) ? `0${first.getMonth() + 1}` : first.getMonth() + 1}-${(first.getDate() < 10) ? `0${first.getDate()}` : first.getDate()}`;
            this.searchForm.dateTo = `${last.getFullYear()}-${(last.getMonth() < 10) ? `0${last.getMonth() + 1}` : last.getMonth() + 1}-${(last.getDate() < 10) ? `0${last.getDate()}` : last.getDate()}`;
            break;
        }
      },
      _handleRefreshButtonClick() {
        this.searchForm.page = 1;
        this.searchImport();
      },
      _handleButtonSearchClick() {
        this.$root.$bvToast.toast('Toast body content', {
          title: `Variant`,
          variant: 'danger',
          solid: true,
        })
        let dFrom = new Date(this.searchForm.dateFrom);
        let dTo = new Date(this.searchForm.dateTo);
        if (dTo < dFrom) {
          console.log('fèaef')
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
            console.log(data);
            this.importMaterialDetail = data;
            this.$bvModal.show('inventory_material-detail');
          }).catch(err => {
            alert(err)
        })
      }
    }
  }
</script>
