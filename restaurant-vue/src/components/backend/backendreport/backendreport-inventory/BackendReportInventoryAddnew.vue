<template>
  <b-modal id="report_inventory_new" @show="initReportInventoryImport" size="xl" hide-footer no-close-on-backdrop no-close-on-esc hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fas fa-plus"></i>
        Tạo mới kiểm kê
      </div>
      <div class="modal-head__close" @click="_handleCancelButton">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã phiếu kiểm kê <span class="starr">*</span>
          </label>
          <input :maxlength="50" v-model="inventoryData.inventoryCode">
        </div>
        <div class="an-item">
          <label>
            Ngày kiểm kê <span class="starr">*</span>
          </label>
          <input type="date" v-model="inventoryData.importDate">
        </div>
      </div>
      <div class="an-material">
        <div class="an-material__title">
          Nguyên vật liệu
        </div>
        <table class="an-material__table">
          <thead>
          <tr>
            <th></th>
            <th>Tên NVL</th>
            <th>Nhóm</th>
            <th>Đơn vị tính</th>
            <th>Số lượng trên hệ thống</th>
            <th>Số lượng kiểm kê</th>
            <th>Chênh lệch</th>
            <th>Nguyên nhân</th>
            <th>Xử lý</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <template v-if="inventoryData.materials.length > 0">
            <tr v-for="(inventoryM, key) in inventoryData.materials" :key="key">
              <td>
              </td>
              <td>
                <select v-if="materials" v-model="inventoryM.material" class="td-select"
                        @focus="_handleSelectMaterialFocus(key)" @change="_handleSelectMaterialChange(key)">
                  <option disabled selected :value="null">Chọn nguyên vật liệu</option>
                  <template v-for="(material, key) in materials">
                    <option v-if="!material.isSelected" :value="material" :key="key">
                      {{ material.materialName }}
                    </option>
                  </template>
                </select>
              </td>
              <td>
                {{
                  (inventoryM.material) ? `${((inventoryM.material.groupMaterial && inventoryM.material.groupMaterial.groupName) ? inventoryM.material.groupMaterial.groupName : '- -')}` : ''
                }}
              </td>
              <td>
                {{ (inventoryM.material) ? `${inventoryM.material.unitPrice ? number_with_commas(inventoryM.material.unitPrice) : '- -'}đ/${inventoryM.material.unit ? inventoryM.material.unit : '- -'}` : '' }}
              </td>
              <td>
                {{ (inventoryM.material) ? (inventoryM.material.remain ? insertCommasDecimal(inventoryM.material.remain) : 0) : '' }}
              </td>
              <td>
                <template v-if="inventoryM.material">
                  <input v-mask="mask_decimal_limit(5)" type="text" v-model="inventoryM.inventoryQuantity"
                         class="td-input" @keyup="_handleInventoryQuantityInput(inventoryM.inventoryQuantity, key)">
                </template>
              </td>
              <td>
                <template v-if="inventoryM.material">
                  {{ inventoryM.different ? insertCommasDecimal(inventoryM.different) : 0 }}
                </template>
              </td>
              <td>
                <template v-if="inventoryM.material">
                  <input :maxlength="200" type="text" class="td-input" v-model="inventoryM.why">
                </template>
              </td>
              <td>
                <template v-if="inventoryM.material && inventoryM.inventoryQuantity">
                  <template v-if="inventoryM.material.remain < parseFloat(remove_hyphen(inventoryM.inventoryQuantity))">
                    Nhập kho
                  </template>
                  <template v-else-if="inventoryM.material.remain > parseFloat(remove_hyphen(inventoryM.inventoryQuantity))">
                    Xuất kho
                  </template>
                </template>
              </td>
              <td>
                <button @click="_handleMaterialDelete(key)"
                        class="btn-default-green btn-red btn-xs">Xoá
                </button>
              </td>
            </tr>
          </template>
          <tr>
            <td>
              <span class="add-new" @click="_handleAddNewMaterial"><i class="fad fa-plus-circle"></i></span>
            </td>
            <td colspan="9"></td>
          </tr>
          </tbody>
        </table>
      </div>
      <b-alert class="mt-4" v-model="formError.isShow" variant="danger" dismissible>
        <ul class="mb-0" v-if="formError.list.length > 0">
          <li v-for="(item, key) in formError.list" :key="key">
            {{ item }}
          </li>
        </ul>
      </b-alert>
      <div class="an-submit">
        <button class="btn-cancel" @click="_handleCancelButton">Huỷ</button>
        <button class="btn-default-green" @click="_handleSaveButtonClick">Tạo mới</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import {
  convert_code,
  check_null,
  check_number,
  isLostConnect,
  number_with_commas,
  insertCommasDecimal,
  remove_hyphen,
  mask_decimal,
  mask_number,
  mask_number_limit,
  mask_decimal_limit
} from "../../../../static";

export default {
  name: 'BackendReportInventoryAddnew',
  props: ['initInventoryMaterial'],
  data() {
    return {
      inventoryData: {
        inventoryCode: null,
        importDate: null,
        materials: []
      },
      termMaterial: null,
      materials: null,
      formError: {
        list: [],
        isShow: false
      },
      mask_decimal,
      mask_number,
      mask_number_limit,
      mask_decimal_limit,
    }
  },
  created() {
    this.initReportInventoryImport();
  },
  methods: {
    number_with_commas,
    insertCommasDecimal,
    remove_hyphen,
    initReportInventoryImport() {
      this.inventoryData = {
        inventoryCode: null,
        importDate: null,
        materials: []
      };
      this.termMaterial = null;
      this.materials = null;
      this.getAllMaterial();
    },
    getAllMaterial() {
      this.$store.dispatch('getAllMaterial')
        .then(({data}) => {
          data.map(item => {
            item['isSelected'] = false;
            return item;
          })
          this.materials = data;
          this._initImportDate();
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      });
    },
    _initImportDate() {
      let curr = new Date;
      let first = new Date(Date.UTC(curr.getFullYear(), curr.getMonth(), curr.getDate()));
      this.inventoryData.importDate = `${first.getFullYear()}-${(first.getMonth() < 10) ? `0${first.getMonth() + 1}` : first.getMonth() + 1}-${(first.getDate() < 10) ? `0${first.getDate()}` : first.getDate()}`;
    },
    _handleAddNewMaterial() {
      this.inventoryData.materials.push({
        material: null,
        materialBackup: null,
        inventoryQuantity: null,
        different: null,
        why: null,
        solution: 0,
      });
    },
    _handleSelectMaterialFocus(key) {
      this.termMaterial = this.inventoryData.materials[key].material;
    },
    _handleSelectMaterialChange(key) {
      let thisId = this.inventoryData.materials[key].material.materialId;
      this.inventoryData.materials.forEach((m, index) => {
        if (m.material && key !== index && m.material.materialId === thisId) {
          this.inventoryData.materials[key].material = this.termMaterial;
          this.$swal({
            position: 'top-end',
            icon: 'warning',
            title: `${m.material.materialName} đã được chọn trước đó`,
            showConfirmButton: false,
            timer: 5000,
            toast: true,
          })
        }
      })
    },
    _handleMaterialQuantityChange(key) {
      if (this.inventoryData.materials[key].material !== null) {
        this.inventoryData.materials[key].price =
          Math.ceil(remove_hyphen(this.inventoryData.materials[key].material.unitPrice)) *
          Math.ceil(remove_hyphen(this.inventoryData.materials[key].quantityImport));
        this.inventoryData.materials[key].sumPrice =
          this.inventoryData.materials[key].price * 2;
        this.sumMaterialCost();
      }
    },
    _handleInventoryQuantityInput(inventoryQuantity, key) {
      let inventoryQuantityNumber = !check_null(inventoryQuantity) ? parseFloat(remove_hyphen(inventoryQuantity)) : 0;
      if (this.inventoryData.materials[key].material.remain > inventoryQuantityNumber) {
        this.inventoryData.materials[key].different = this.inventoryData.materials[key].material.remain - inventoryQuantityNumber;
      } else {
        this.inventoryData.materials[key].different = inventoryQuantityNumber - this.inventoryData.materials[key].material.remain;
      }
    },
    _handleMaterialDelete(key) {
      this.inventoryData.materials.splice(key, 1);
    },
    _handleSaveButtonClick() {
      this.formError = {
        list: [],
        isShow: false
      }
      if (check_null(this.inventoryData.inventoryCode)) {
        this.formError.list.push('Mã phiếu kiểm kê không được để trống');
        this.formError.isShow = true;
      }
      if (check_null(this.inventoryData.importDate)) {
        this.formError.list.push('Ngày kiểm kê không được để trống');
        this.formError.isShow = true;
      }
      if (this.inventoryData.materials.length <= 0) {
        this.formError.list.push('Hãy chọn ít nhất một nguyên vật liệu');
        this.formError.isShow = true;
      } else this.inventoryData.materials.forEach((item, key) => {
        if (item.material === null) {
          this.formError.list.push(`Nguyên vật liệu ${key + 1} không được để trống`);
          this.formError.isShow = true;
        } else {
          if (check_null(item.inventoryQuantity) || item.inventoryQuantity <= 0) {
            this.formError.list.push(`Số lượng kiểm kê của ${item.material.materialName} không được để trống`);
            this.formError.isShow = true;
          }
          if (check_null(item.why)) {
            this.formError.list.push(`Nguyên nhân chênh lệch của ${item.material.materialName} không được để trống`);
            this.formError.isShow = true;
          }
        }
      })
      if (!this.formError.isShow) {
        let requestData = [];
        this.inventoryData.materials.forEach(item => {
          if (item.material && item.inventoryQuantity) {
            if (item.material.remain < parseFloat(remove_hyphen(item.inventoryQuantity)) ) {
              item.solution = 1;
            } else if (item.material.remain > parseFloat(remove_hyphen(item.inventoryQuantity))) {
              item.solution = 2;
            } else {
              item.solution = 0;
            }
          } else {
            item.solution = 0;
          }
          requestData.push({
            inventoryCode: !check_null(this.inventoryData.inventoryCode) ? this.inventoryData.inventoryCode : '',
            inventoryDate: !check_null(this.inventoryData.importDate) ? this.inventoryData.importDate : '',
            materialId: item.material.materialId,
            materialName: item.material.materialName,
            materialUnit: item.material.unit,
            remainSystem: item.material.remain,
            remainFact: !check_null(item.inventoryQuantity) ? parseFloat(remove_hyphen(item.inventoryQuantity)) : 0,
            quantityDifferent: !check_null(item.different) ? item.different : 0,
            reason: !check_null(item.why) ? item.why : '',
            process: item.solution
          })
        })
        this.$store.dispatch('addNewInventoryMaterial', requestData)
          .then(response => {
            console.log(response.data)
            this.$swal(`Tạo mới thành công`,
              'Danh sách kiểm kê đã được cập nhật lên hệ thống.',
              'success').then(result => {
              if (result.value) {
                this.initInventoryMaterial();
                this._handleCancelButton();
              }
            })
          }).catch(error => {
          if (!isLostConnect(error, false)) {
            this.$swal({
              title: 'Có lỗi sảy ra',
              html: 'Vui lòng thử lại',
              icon: 'warning',
              showCloseButton: true,
              confirmButtonText: 'Đóng',
            });
          }
        })
      }
    },
    _handleCancelButton() {
      this.$bvModal.hide('report_inventory_new');
    }
  }
}
</script>

<style scoped>

</style>
