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
    <div class="modal-body"
         v-if="materialData !== null && groupMaterials !== null && suppliers !== null && warehouses !== null">
      <div class="an-form">
        <div class="an-item">
          <label>
            Mã phiếu <span class="starr">*</span>
          </label>
          <input v-model="materialData.importCode">
        </div>
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
          <input type="text" v-model="materialData.materialName" @keyup="_handleNameChange">
        </div>
        <div class="an-item">
          <label>
            Đơn vị <span class="starr">*</span>
          </label>
          <input type="text" v-model="materialData.unit">
        </div>
        <div class="an-item">
          <label>
            Số lượng nhập <span class="starr">*</span>
          </label>
          <input v-mask="mask_decimal" v-model="materialData.totalImport" @keyup="_handleTotalPriceChange">
        </div>
        <div class="an-item">
          <label>
            Hàng tồn tối thiểu
          </label>
          <input v-mask="mask_decimal" v-model="materialData.remainNotification">
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
            Giá nhập <span class="starr">*</span>
          </label>
          <div class="left-input">
            <input v-mask="mask_number" v-model="materialData.unitPrice" @keyup="_handleTotalPriceChange">
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
          <input v-mask="mask_number" disabled v-model="materialData.totalPrice">
        </div>
        <div class="an-item">
          <label>
            Hạn sử dụng (ngày)
          </label>
          <input v-mask="mask_number" v-model="materialData.expiredDate">
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
  import {
    convert_code,
    check_number,
    check_null,
    mask_number,
    mask_decimal,
    remove_hyphen,
  } from "../../../static";

  export default {
    name: 'BackendInventoryAddNew',
    props: ['initInventory'],
    data() {
      return {
        importCode: null,
        materialData: null,
        groupMaterials: null,
        suppliers: null,
        warehouses: null,
        formError: {
          list: [],
          isShow: false
        },
        mask_decimal,
        mask_number
      }
    },
    created() {
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
      this.initNewInventoryData();
    },
    methods: {
      initNewInventoryData() {
        this.materialData = {
          importCode: null,
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
        if (remove_hyphen(this.materialData.totalImport) > 0 && remove_hyphen(this.materialData.unitPrice) > 0) {
          this.materialData.totalPrice = remove_hyphen(this.materialData.totalImport) * remove_hyphen(this.materialData.unitPrice);
        } else {
          this.materialData.totalPrice = 0;
        }
      },
      _handleAddnewButton() {
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(this.materialData.importCode)) {
          this.formError.list.push('Mã phiếu không được để trống');
          this.formError.isShow = true;
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
            importCode: this.materialData.importCode,
            totalAmount: parseFloat(remove_hyphen(this.materialData.totalPrice)),
            comment: this.materialData.description,
            supplierId: this.materialData.supplier,
            importMaterial: {
              quantityImport: parseFloat(remove_hyphen(this.materialData.totalImport)),
              sumPrice: parseFloat(remove_hyphen(this.materialData.totalPrice)),
              expireDate: parseFloat(remove_hyphen(this.materialData.expiredDate)),
              warehouseId: this.materialData.warehouse,
              material: {
                materialCode: this.materialData.materialCode,
                materialName: this.materialData.materialName,
                unit: this.materialData.unit,
                unitPrice: parseFloat(remove_hyphen(this.materialData.unitPrice)),
                remainNotification: parseFloat(remove_hyphen(this.materialData.remainNotification)),
                groupMaterialId: (this.materialData.groupMaterial > 0) ? this.materialData.groupMaterial : null
              }
            }
          };
          this.$store.dispatch('insertImportInventory', {inventoryData: requestData})
            .then(response => {
              console.log(response.data);
              this.$swal('Thành công!',
                'Nguyên vật liệu đã được cập nhật lên hệ thống.',
                'success').then((result) => {
                if (result.value) {
                  this.initInventory();
                  this.$bvModal.hide('inventory_edit');
                }
              })
            }).catch(err => {
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
