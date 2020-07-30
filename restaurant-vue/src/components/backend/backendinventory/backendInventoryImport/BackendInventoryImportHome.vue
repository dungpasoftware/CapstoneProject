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
        <select defaultValue="0" class="select__type">
          <option value="1">
            Trong ngày
          </option>
        </select>
        <input type="date" class="select__name"/>
        <input type="date" class="select__name"/>
        <select defaultValue="0" class="select__type">
          <option value="1">
            Nhà cung cấp
          </option>
        </select>
        <button class="select__search btn-default-green">
          Tìm kiếm
        </button>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-file-alt"></i>
        Báo cáo
      </div>
      <div class="list-body">
        <div class="list__option">
          <button class="btn-default-green">
            Làm mới
          </button>
          <button class="btn-default-green btn-red">
            Xoá danh sách đã chọn
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
            <th> </th>
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
                {{ (report.supplier !== null && report.supplier.supplierName !== null) ? report.supplier.supplierName : '' }}
              </td>
              <td :rowspan="report.importMaterials.length" v-if="mKey === 0">
                {{ (report.createdDate !== null) ? report.createdDate : '' }}
              </td>
              <td>
                {{ (materialReport.material !== null && materialReport.material.materialName !== null) ? materialReport.material.materialName : '' }}
              </td>
              <td>
                <div class="table__option table__option-inline">
                  <button class="btn-default-green btn-xs table__option--delete">
                    Chi tiết
                  </button>
                </div>
              </td>
            </tr>
            </tbody>
          </template>
        </table>
      </div>
    </div>
    <BackendInventoryImportAddNew/>
  </div>
</template>

<script>
  import * as staticFunction from '../../../../static'
  import BackendInventoryImportAddNew from "./BackendInventoryImportAddNew";


  export default {
    data() {
      return {
        importReports: null,
      };
    },
    components: {
      BackendInventoryImportAddNew
    },
    created() {
      this.initImportReports();
    },
    methods: {
      initImportReports() {
        this.$store.dispatch('getAllInventory')
          .then(response => {
            this.importReports = response.data
            console.log(this.importReports)
          }).catch(err => {
          console.error(err)
        })
      },
    }
  }
</script>
