<template>
  <b-modal id="inventory_edit" @shown="initGroupMaterialData" size="lg" hide-footer hide-header no-close-on-backdrop
           no-close-on-esc centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fad fa-plus-hexagon"></i>
        Chỉnh sửa nguyên vật liệu
      </div>
      <div class="modal-head__close" @click="_handleCloseModal">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-body">
      <div class="an-form" v-if="materialEditData !== null">
        <div class="an-item">
          <label>
            Mã NVL <span class="starr"></span>
          </label>
          <input :maxlength="100" type="text" disabled v-model="materialEditData.materialCode">
        </div>
        <div class="an-item">
          <label>
            Tên NVL <span class="starr">*</span>
          </label>
          <input :maxlength="100" type="text" v-model="materialEditData.materialName">
        </div>
        <div class="an-item">
          <label>
            Đơn vị <span class="starr">*</span>
          </label>
          <input :maxlength="50" type="text" v-model="materialEditData.unit">
        </div>
        <div class="an-item">
          <label>
            Giá nhập <span class="starr">*</span>
          </label>
          <div class="left-input">
            <input class="textalign-right" v-mask="mask_number_limit(13)" v-model="materialEditData.unitPrice"
                   @input="_handleTotalPriceChange">
            <template v-if="materialEditData.unit !== ''">
              <span>/</span>
              <div>{{materialEditData.unit}}</div>
            </template>
          </div>
        </div>
        <div class="an-item">
          <label>
            Nhóm
          </label>
          <select defaultvalue="0" v-model="materialEditData.groupMaterial.groupId">
            <option :value="null">Chọn nhóm</option>
            <option v-if="groupMaterials !== null && groupMaterials.length > 0"
                    v-for="(groupMaterial, key) in groupMaterials" :key="key"
                    :value="groupMaterial.groupId">{{groupMaterial.groupName}}
            </option>
          </select>
        </div><div class="an-item">
        <label>
          Hàng tồn tối thiểu
        </label>
        <input v-mask="mask_decimal_limit(5)" v-model="materialEditData.remainNotification">
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
        <button class="btn-cancel" @click="_handleCloseModal">Huỷ</button>
        <button class="btn-default-green" @click="_handleSaveEditMaterial">Cập nhật</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import {
  convert_code,
  check_null,
  mask_number_limit,
  mask_decimal_limit,
  remove_hyphen, isLostConnect
} from "../../../static";

  export default {
    name: 'BackendInventoryEdit',
    data() {
      return {
        materialData: null,
        groupMaterials: null,
        formError: {
          list: [],
          isShow: false
        },
        mask_decimal_limit,
        mask_number_limit
      }
    },
    props: ['materialEditData', 'initInventory'],
    created() {
      this.initGroupMaterialData();
    },
    methods: {
      initGroupMaterialData() {
        this.$store.dispatch('getAllGroupMaterial')
          .then(response => {
            this.groupMaterials = response.data;
            this.initMaterialData();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
      },
      initMaterialData() {
        this.materialData = this.materialEditData
      },
      _handleTotalPriceChange() {
        if (this.materialEditData.totalImport > 0 && this.materialEditData.unitPrice > 0) {
          this.materialEditData.totalPrice = this.materialEditData.totalImport * this.materialEditData.unitPrice;
        } else {
          this.materialEditData.totalPrice = 0;
        }
      },
      _handleSaveEditMaterial() {
        let materialEditDataRequest = {
          materialId: this.materialEditData.materialId,
          materialCode: this.materialEditData.materialCode,
          materialName: this.materialEditData.materialName,
          unit: this.materialEditData.unit,
          unitPrice: parseFloat(remove_hyphen(this.materialEditData.unitPrice)),
          remainNotification: parseFloat(remove_hyphen(this.materialEditData.remainNotification)),
          groupMaterialId: (this.materialEditData.groupMaterial.groupId > 0) ? this.materialEditData.groupMaterial.groupId : null
        }
        console.log(materialEditDataRequest)
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(materialEditDataRequest.materialName)) {
          this.formError.list.push('Tên NVL không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(materialEditDataRequest.unit)) {
          this.formError.list.push('Đơn vị không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(materialEditDataRequest.unitPrice)) {
          this.formError.list.push('Giá nhập không được để trống');
          this.formError.isShow = true;
        }
        if (!this.formError.isShow) {
          this.$store.dispatch('editMaterialById', materialEditDataRequest)
            .then(response => {
              this.$swal('Thành công!',
                'Nguyên vật liệu đã được cập nhật lên hệ thống.',
                'success').then((result) => {
                  this.initInventory();
                  this.$bvModal.hide('inventory_edit');
              })
            }).catch(error => {
            if (!isLostConnect(error, false)) {
              this.$swal({
                title: 'Có lỗi xảy ra',
                html: 'Vui lòng thử lại',
                icon: 'warning',
                showCloseButton: true,
                confirmButtonText: 'Đóng',
              });
            }
          })
        }

      },
      _handleCloseModal() {
        this.$bvModal.hide('inventory_edit');
      }
    }
  }
</script>
