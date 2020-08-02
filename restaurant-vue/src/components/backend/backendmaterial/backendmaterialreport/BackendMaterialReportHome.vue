<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-file-alt"></i>
        Danh sách báo cáo
      </div>
      <div class="list-body">
        <div class="list__option">
          <button @click="initMaterials" class="btn-default-green">
            Làm mới
          </button>
        </div>
        <table class="list__table">
          <thead>
          <tr>
            <th> STT</th>
            <th> Tên NVL</th>
            <th> Nhóm</th>
            <th> Đơn vị</th>
            <th> SL nhập</th>
            <th> SL xuất</th>
            <th> Còn lại</th>
            <th> Chi tiết</th>
          </tr>
          </thead>
          <tbody v-if="materials !== null">
          <tr v-for="(material, key) in materials" :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              {{ (material.materialName !== null) ? material.materialName : '' }}
            </td>
            <td>
              {{ (material.groupMaterial !== null && material.groupMaterial.groupName !== null) ?
              material.groupMaterial.groupName :
              '- -' }}
            </td>
            <td>
              {{ (material.unit !== null) ? material.unit : '' }}
            </td>
            <td>
              {{ (material.totalImport !== null) ? number_with_commas(material.totalImport) : '' }}
            </td>
            <td>
              {{ (material.totalExport !== null) ? number_with_commas(material.totalExport) : '' }}
            </td>
            <td>
              <div class="color-red" v-if="showAlertRemain(material.remain, material.remainNotification)"
                   v-b-popover.hover.bottom="'Số lượng còn lại quá ít'">
                <i class="fad fa-engine-warning"></i>
                {{ (material.remain !== null && material.remainNotification !== null) ?
                number_with_commas(material.remain)
                : 0 }}
              </div>
              <div v-else>
                {{ (material.remain !== null && material.remainNotification !== null) ?
                number_with_commas(material.remain)
                : 0 }}
              </div>
            </td>
            <td>
              <div class="table__option table__option-inline">
                <button @click="_handleShowMaterialReportDetailClick(material.materialId, material)"
                  class="btn-default-green btn-xs table__option--delete">
                  Xem
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <BackendInventoryImportMaterialDetail :material="material" :materialReport="materialReport"/>
  </div>
</template>

<script>
  import {xoa_dau, number_with_commas} from "../../../../static";
  import BackendInventoryImportMaterialDetail from "./BackendMaterialReportDetail";

  export default {
    data() {
      return {
        materials: null,
        material: null,
        materialReport: null,
      };
    },
    components: {
      BackendInventoryImportMaterialDetail
    },
    created() {
      this.initMaterials();
    },
    methods: {
      number_with_commas,
      initMaterials() {
        this.$store.dispatch('getAllMaterial')
          .then(({data}) => {
            console.log(data)
            this.materials = data
          }).catch(err => {
          console.error(err)
        })
      },
      showAlertRemain(remain, remainNoti) {
        return remain <= remainNoti;
      },
      _handleShowMaterialReportDetailClick(id, materialDetail) {
        this.$store.dispatch('getMaterialReportDetail', id)
          .then(({data}) => {
            this.material = materialDetail;
            this.materialReport = data;
            this.$bvModal.show('material_report_detail');
          }).catch(err => {
            console.error(err)
        })
      }
    }
  }
</script>
