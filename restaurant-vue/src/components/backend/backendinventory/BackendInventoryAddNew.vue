<template>
  <b-modal id="inventory_add_new" size="xl" hide-footer hide-header no-close-on-backdrop no-close-on-esc centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fad fa-plus-hexagon"></i>
        Tạo mới tồn kho
      </div>
      <div class="modal-head__close" @click="_handleCancelButton">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body" v-if="materialData !== null && groupMaterials !== null && suppliers !== null && warehouses !== null">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã NVL <span class="starr"></span>
          </label>
          <input type="text" disabled v-model="materialData.materialCode">
        </div>
        <div class="an-item">
          <label>
            Tên NVL <span class="starr">*</span>
          </label>
          <input type="text" v-model="materialData.materialName" v-on:input="_handleNameChange">
        </div>
        <div class="an-item">
          <label>
            Lượng hàng tồn <span class="starr">*</span>
          </label>
          <input type="number" v-model="materialData.totalImport" @keypress="_handleCheckNumber($event)"
                 @input="_handleTotalPriceChange">
        </div>
        <div class="an-item">
          <label>
            Hàng tồn tối thiểu
          </label>
          <input type="number" v-model="materialData.remainNotification" @keypress="_handleCheckNumber($event)">
        </div>
        <div class="an-item">
          <label>
            Nhóm
          </label>
          <select :defaultvalue="0" v-model="materialData.groupMaterial">
            <option value="0" disabled selected>Chọn nhóm</option>
            <option v-if="groupMaterials !== null && groupMaterials.length > 0"
                    v-for="(groupMaterial, key) in groupMaterials" :key="key"
                    :value="groupMaterial.groupId">{{groupMaterial.groupName}}
            </option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Nhà cung cấp
          </label>
          <select :defaultvalue="0" v-model="materialData.supplier">
            <option value="0" disabled selected>Chọn nhà cung cấp</option>
            <option v-if="suppliers !== null && suppliers.length > 0"
                    v-for="(supplier, key) in suppliers" :key="key"
                    :value="supplier.supplierId">{{supplier.supplierName}}
            </option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Kho
          </label>
          <select defaultvalue="0" v-model="materialData.warehouse">
            <option value="0" disabled selected>Chọn kho</option>
            <option v-if="warehouses !== null && warehouses.length > 0"
                    v-for="(warehouse, key) in warehouses" :key="key"
                    :value="warehouse.warehouseId">{{warehouse.name}}
            </option>
          </select>
        </div>
        <div class="an-item">
          <label>
            Đơn vị <span class="starr">*</span>
          </label>
          <input type="text" v-model="materialData.unit">
        </div>
        <div class="an-item">
          <label>
            Giá nhập <span class="starr">*</span>
          </label>
          <div class="left-input">
            <input type="number" v-model="materialData.unitPrice" @keypress="_handleCheckNumber($event)"
                   @input="_handleTotalPriceChange">
            <template v-if="materialData.unit !== ''">
              <span>/</span>
              <div>{{materialData.unit}}</div>
            </template>
          </div>
        </div>
        <div class="an-item">
          <label>
            Tổng giá nhập
          </label>
          <input type="text" disabled v-model="materialData.totalPrice">
        </div>
        <div class="an-item">
          <label>
            Hạn sử dụng (ngày)
          </label>
          <input type="number" v-model="materialData.expiredDate" @keypress="_handleCheckNumber($event)">
        </div>
        <div class="an-item">
          <label>
            Ghi chú
          </label>
          <textarea v-model="materialData.description" rows="3"></textarea>
        </div>
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
        <button class="btn-default-green" @click="_handleAddnewButton">Tạo mới</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
  import {convert_code, check_number, check_null} from "../../../static";

  export default {
    name: 'BackendInventoryAddNew',
    data() {
      return {
        materialData: null,
        materialDefault: {
          materialCode: null,
          materialName: null,
          totalImport: null,
          remainNotification: null,
          groupMaterial: null,
          supplier: null,
          warehouse: null,
          unit: null,
          unitPrice: null,
          totalPrice: null,
          expiredDate: null,
          description: null
        },
        groupMaterials: null,
        suppliers: null,
        warehouses: null,
        formError: {
          list: [],
          isShow: false
        }
      }
    },
    created() {
      this.initNewInventoryData();
      this.$store.dispatch('getAllGroupMaterial')
        .then(({data}) => {
          this.groupMaterials = data;
        }).catch(err => {
        console.log(err);
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
      initNewInventoryData() {
        this.materialData = {
          materialCode: null,
          materialName: null,
          totalImport: null,
          remainNotification: null,
          groupMaterial: null,
          supplier: null,
          warehouse: null,
          unit: null,
          unitPrice: null,
          totalPrice: null,
          expiredDate: null,
          description: null
        };
      },
      _handleNameChange() {
        this.materialData.materialCode = convert_code(this.materialData.materialName);
      },
      _handleCheckNumber(e) {
        return check_number(e);
      },
      _handleTotalPriceChange() {
        if (this.materialData.totalImport > 0 && this.materialData.unitPrice > 0) {
          this.materialData.totalPrice = this.materialData.totalImport * this.materialData.unitPrice;
        } else {
          this.materialData.totalPrice = 0;
        }
      },
      _handleAddnewButton() {
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(this.materialData.materialName)) {
          this.formError.list.push('Tên NVL không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.materialData.totalImport)) {
          this.formError.list.push('Lượng hàng tồn không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.materialData.unit)) {
          this.formError.list.push('Đơn vị không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.materialData.unitPrice)) {
          this.formError.list.push('Giá nhập không được để trống');
          this.formError.isShow = true;
        }

        if (!this.formError.isShow) {
          let requestData = {
            totalAmount: this.materialData.totalPrice,
            comment: this.materialData.description,
            supplierId: this.materialData.supplier,
            importMaterials: [
              {
                quantityImport: this.materialData.totalImport,
                price: this.materialData.unitPrice,
                unitPrice: this.materialData.totalPrice,
                expireDate: this.materialData.expiredDate,
                warehouseId: this.materialData.warehouse,
                material: {
                  materialCode: this.materialData.materialCode,
                  materialName: this.materialData.materialName,
                  unit: this.materialData.unit,
                  remainNotification: this.materialData.remainNotification,
                  groupMaterialId: (this.materialData.groupMaterial > 0) ? this.materialData.groupMaterial : null
                }
              }
            ]
          };
          this.$store.dispatch('insertImportInventory', {inventoryData: requestData})
            .then(response => {
              this.$swal('Thành công!',
                'Nguyên vật liệu đã được cập nhật lên hệ thống.',
                'success').then((result) => {
                if (result.value) {
                  this.initInventory();
                  this.$bvModal.hide('inventory_edit');
                }
              })
            }).catch(err => {
            console.error(err)
            this.formError.list.push(err.message);
            this.formError.isShow = true;
          })
        }
      },
      _handleCancelButton() {
        this.initNewInventoryData();
        this.$bvModal.hide('inventory_add_new');
      }
    }
  }
</script>
