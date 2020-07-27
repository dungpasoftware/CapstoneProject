<template>
  <b-modal id="inventory_import_new" size="xl" hide-footer no-close-on-backdrop no-close-on-esc hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fad fa-file-import"></i>
        Phiếu nhập kho
      </div>
      <div class="modal-head__close" @click="$bvModal.hide('inventory_import_new')">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã phiếu
          </label>
          <input type="text">
        </div>
        <div class="an-item-vue-select">
          <label>
            Nhà cung cấp
          </label>
          <v-select multiple="" :options="['Canada', 'United States']"></v-select>
        </div>
        <div class="an-item">
          <label>
            Tổng giá
          </label>
          <input type="number" v-model="importData.totalAmount" disabled>
        </div>
        <div class="an-item">
          <label>
            Ghi chú
          </label>
          <textarea rows="3"></textarea>
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
            <th>Định lượng</th>
            <th>Đơn giá * Định lượng</th>
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
                          label="materialName" @input="_handleMaterialSelect(key)"
                          :reduce="material => material"
                          v-model="importM.material"
                          :options="materials">
                </v-select>
              </td>
              <td>
                <template v-if="importM.material !== null">
                  {{ (importM.material.unitPrice !== null) ? formatNumber(importM.material.unitPrice) : ''}}đ
                  /
                  {{ (importM.material.unit !== null) ? importM.material.unit : '' }}
                </template>
              </td>
              <td>
                <div v-if="importM.material !== null && importM.material.materialId !== null" style="width: 100%; display: flex; align-items: center">
                  <input type="number" class="td-input mr-1" v-model="importM.quantityImport"
                         @input="_handleMaterialQuantityChange(key)"
                         @keypress="_handleCheckNumber($event)">
                  ({{ (importM.material.unit !== null) ? importM.material.unit : '' }})
                </div>
              </td>
              <td>
                {{ (importM.price !== null) ? formatNumber(importM.price) : 0 }}đ
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
                <input type="number" class="td-input" v-model="importM.expiredDate"
                       @keypress="_handlePhoneChange($event)">
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
      <div class="an-submit">
        <button class="btn-cancel" @click="$bvModal.hide('inventory_import_new')">Huỷ</button>
        <button class="btn-default-green">Tạo mới</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
  import {check_number, number_with_commas} from "../../../../static";

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
        warehouses: null
      }
    },
    created() {
      this.$store.dispatch('getAllMaterial')
        .then(({data}) => {
          console.log(data)
          this.materials = data;
        }).catch(error => {
        console.log(error)
      });
      this.$store.dispatch('getAllSupplier')
        .then(({data}) => {
          this.suppliers = data;
        }).catch(err => {
        console.log(err);
      });
      this.$store.dispatch('getAllWarehouse')
        .then(({data}) => {
          this.warehouses = data;
        }).catch(err => {
        console.log(err);
      });
    },
    methods: {
      formatNumber(number) {
        return number_with_commas(number);
      },
      sumMaterialCost() {
        this.importData.totalAmount = 0;
        this.importData.totalAmount = this.importData.importMaterials.reduce((sum, addItem) => {
          return sum += (addItem.sumPrice > 0) ? addItem.sumPrice : 0;
        }, 0);
      },
      _handleCheckNumber(e) {
        return check_number(e);
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
      _handleMaterialSelect(key) {
        console.log(this.importData.importMaterials[key])
      },
      _handleMaterialQuantityChange(key) {
        if (this.importData.importMaterials[key].material !== null) {
          console.log(this.importData.importMaterials[key].material.unitPrice, this.importData.importMaterials[key].quantityImport)
          this.importData.importMaterials[key].price =
            this.importData.importMaterials[key].material.unitPrice *
            this.importData.importMaterials[key].quantityImport;
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

      }
    }
  }
</script>

<style scoped>

</style>
