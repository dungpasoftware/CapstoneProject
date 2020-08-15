<template>
  <b-modal id="inventory_import_new" size="xl" hide-footer no-close-on-backdrop no-close-on-esc hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fad fa-file-import"></i>
        Phiếu nhập kho
      </div>
      <div class="modal-head__close" @click="_handleCancelButton">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã phiếu <span class="starr">*</span>
          </label>
          <input :maxlength="150" v-model="importData.importCode">
        </div>
        <div class="an-item-vue-select">
          <label>
            Nhà cung cấp
          </label>
          <v-select :reduce="supplier => supplier.supplierId"
                    v-model="importData.supplierId"
                    label="supplierName" :options="suppliers"></v-select>
        </div>
        <div class="an-item">
          <label>
            Tổng giá <span class="starr">*</span>
          </label>
          <input v-mask="mask_number_limit(20)" v-model="importData.totalAmount">
        </div>
        <div class="an-item">
          <label>
            Ghi chú
          </label>
          <textarea :maxlength="200" v-model="importData.comment" rows="3"></textarea>
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
            <th>Đơn giá / Đơn vị</th>
            <th>Số lượng</th>
            <th>Đơn giá * Số lượng</th>
            <th>Kho</th>
            <th>HSD(ngày)</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <template v-if="importData.importMaterials.length > 0">
            <tr v-for="(importM, key) in importData.importMaterials" :key="key">
              <td>
              </td>
              <td>
                <v-select :placeholder="'Chọn nguyên vật liệu'"
                          label="materialName"
                          :reduce="material => material"
                          v-model="importM.material"
                          :options="materials">
                </v-select>
              </td>
              <td>
                <div v-if="importM.material !== null" style="width: 100%; display: flex; align-items: center; white-space: nowrap">
                  <input v-mask="mask_number_limit(13)" class="td-input mr-1" v-model="importM.material.unitPrice"
                         @keyup="_handleMaterialQuantityChange(key)">
                  đ /
                  {{ (importM.material.unit !== null) ? importM.material.unit : '' }}
                </div>
              </td>
              <td>
                <div v-if="importM.material !== null && importM.material.materialId !== null" style="width: 100%; display: flex; align-items: center">
                  <input v-mask="mask_decimal_limit(5)" class="td-input textalign-right mr-1" v-model="importM.quantityImport"
                         @keyup="_handleMaterialQuantityChange(key)">
                  ({{ (importM.material.unit !== null) ? importM.material.unit : '' }})
                </div>
              </td>
              <td>
                {{ (importM.price !== null) ? number_with_commas(importM.price) : 0 }}đ
              </td>
              <td>
                <select v-model="importM.warehouseId"
                        @change="" class="td-select"
                        v-if="warehouses !== null && warehouses.length > 0">
                  <option disabled selected :value="null">Chọn kho</option>
                  <option v-for="(warehouse, warehouseKey) in warehouses"
                          :key="warehouseKey"
                          :value="warehouse.warehouseId">
                    {{warehouse.name}}
                  </option>
                </select>
              </td>
              <td>
                <input v-mask="mask_number_limit(5)" class="td-input" v-model="importM.expiredDate">
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
            <td colspan="7"></td>
          </tr>
          </tbody>
        </table>
      </div>
      <b-alert class="mt-4" v-model="formError.isShow" variant="danger" dismissible>
        <ul class="mb-0" v-if="formError.list.length > 0">
          <li v-for="(item, key) in formError.list" :key="key">
            {{item}}
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
  check_null,
  check_number,
  isLostConnect,
  number_with_commas,
  remove_hyphen,
  mask_number_limit,
  mask_decimal_limit
} from "../../../../static";

  export default {
    name: 'BackendInventoryImportAddNew',
    data() {
      return {
        importData: {
          importCode: null,
          supplierId: null,
          totalAmount: null,
          comment: null,
          importMaterials: []
        },
        materials: null,
        suppliers: null,
        warehouses: null,
        formError: {
          list: [],
          isShow: false
        },
        mask_number_limit,
        mask_decimal_limit
      }
    },
    created() {
      this.getAllMaterial();
    },
    methods: {
      number_with_commas,
      getAllMaterial() {
        this.$store.dispatch('getAllMaterial')
          .then(({data}) => {
            this.materials = data;
            this.getAllSupplier();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        });
      },
      getAllSupplier() {
        this.$store.dispatch('getAllSupplier')
          .then(({data}) => {
            this.suppliers = data;
            this.getAllWarehouse();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        });
      },
      getAllWarehouse() {
        this.$store.dispatch('getAllWarehouse')
          .then(({data}) => {
            this.warehouses = data;
            this.initNewImportData();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        });
      },
      initNewImportData() {
        this.importData = {
          importCode: null,
          supplierId: null,
          totalAmount: null,
          comment: null,
          importMaterials: []
        }
      },
      sumMaterialCost() {
        this.importData.totalAmount = 0;
        this.importData.totalAmount = this.importData.importMaterials.reduce((sum, addItem) => {
          return sum += (addItem.price > 0) ? addItem.price : 0;
        }, 0);
      },
      _handleAddNewMaterial() {
        this.importData.importMaterials.push({
          quantityImport: null,
          price: null,
          sumPrice: null,
          expiredDate: null,
          warehouseId: null,
          materialId: null,
          material: null
        })
      },
      _handleMaterialQuantityChange(key) {
        if (this.importData.importMaterials[key].material !== null) {
          this.importData.importMaterials[key].price =
            Math.ceil(remove_hyphen(this.importData.importMaterials[key].material.unitPrice)) *
            Math.ceil(remove_hyphen(this.importData.importMaterials[key].quantityImport));
          this.importData.importMaterials[key].sumPrice =
            this.importData.importMaterials[key].price * 2;
          this.sumMaterialCost();
        }
      },
      _handleMaterialDelete(key) {
        this.importData.importMaterials.splice(key, 1);
        this.sumMaterialCost();
      },
      _handleSaveButtonClick() {
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(this.importData.importCode)) {
          this.formError.list.push('Mã phiếu không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.importData.totalAmount)) {
          this.formError.list.push('Tổng giá không được để trống');
          this.formError.isShow = true;
        }
        this.importData.importMaterials.forEach((item, key) => {
          if (item.material === null) {
            this.formError.list.push(`Nguyên vật liệu ${key + 1} không được để trống`);
            this.formError.isShow = true;
          }
        })
        if (!this.formError.isShow) {
          let importDataRequest = {
            importCode: !check_null(this.importData.importCode) ? this.importData.importCode : '',
            supplierId: this.importData.supplierId,
            totalAmount: !check_null(this.importData.totalAmount) ? parseFloat(remove_hyphen(this.importData.totalAmount)) : 0,
            comment: !check_null(this.importData.comment) ? this.importData.comment : '',
            importMaterials: this.importData.importMaterials.map(item => {
              let newMaterial = {
                materialId: item.material.materialId,
                quantityImport: !check_null(item.quantityImport) ? parseFloat(remove_hyphen(item.quantityImport)) : 0,
                unitPrice: !check_null(item.material.unitPrice) ? parseFloat(remove_hyphen(item.material.unitPrice)) : 0,
                sumPrice: !check_null(item.price) ? item.price : 0,
                expireDate: !check_null(item.expiredDate) ? parseFloat(remove_hyphen(item.expiredDate)) : 0,
                warehouseId: item.warehouseId,
              }
              return newMaterial;
            })
          }
          this.$store.dispatch('insertImportExistInventory', importDataRequest)
            .then(response => {
              console.log(response.data)
              this.$swal('Thành công!',
                'Dữ liệu kho đã được cập nhật lên hệ thống.',
                'success').then((result) => {
                if (result.value) {
                  this.initNewImportData();
                  this.$bvModal.hide('inventory_import_new');
                }
              })
            }).catch(error => {
            if (!isLostConnect(error, false)) {
              console.log(error.response)
              error.response.data.messages.map(err => {
                this.formError.list.push(err);
                this.formError.isShow = true;
              })
            }
          })
        }
      },
      _handleCancelButton() {
        this.initNewImportData();
        this.$bvModal.hide('inventory_import_new');
      }
    }
  }
</script>

<style scoped>

</style>
