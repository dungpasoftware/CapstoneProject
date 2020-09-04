<template>
  <b-modal id="inventory_material-detail" size="lg" hide-footer hide-header centered>
    <div class="modal-head">
      <div class="modal-head__title">
        <i class="fal fa-file-alt"></i>
        Chi tiết
      </div>
      <div class="modal-head__close" @click="_handleCloseModal">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="modal-report" v-if="importMaterialDetail !== null">
      <div class="modal-report__top">
        <div class="top-item">
          Mã phiếu:
          <span class="top-item__value">
            {{ (importMaterialDetail.importCode !== null) ? importMaterialDetail.importCode : '' }}
          </span>
        </div>
        <div class="top-item">
          Ngày nhập:
          <span class="top-item__value">
            {{ (importMaterialDetail.createdDate !== null) ? importMaterialDetail.createdDate : '' }}
          </span>
        </div>
        <div class="top-item">
          Nhà cung cấp:
          <span class="top-item__value">
            {{ (importMaterialDetail.supplierName !== null) ? importMaterialDetail.supplierName : 'Không có' }}
          </span>
        </div>
      </div>
      <div class="modal-report__body">
        <table class="body-table">
          <thead>
            <tr>
              <th>
                Tên NVL
              </th>
              <th>
                Đơn giá
              </th>
              <th>
                Số lượng
              </th>
              <th>
                Tổng tiền
              </th>
              <th>
                Kho
              </th>
              <th>
                Hạn sử dụng
              </th>
            </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              {{ (importMaterialDetail.materialName !== null) ? importMaterialDetail.materialName : '- -' }}
            </td>
            <td>
              {{ (importMaterialDetail.unitPrice) ? formatNumber(Math.ceil(importMaterialDetail.unitPrice)) : 0 }}đ{{ (importMaterialDetail.unit !== null) ? `/${importMaterialDetail.unit}` : 0 }}
            </td>
            <td>
              {{ (importMaterialDetail.quantity !== null) ? insertCommasDecimal(importMaterialDetail.quantity) : '- -' }}
            </td>
            <td>
              {{ (importMaterialDetail.totalAmount) ? formatNumber(Math.ceil(importMaterialDetail.totalAmount)) : 0 }}đ
            </td>
            <td>
              {{ (importMaterialDetail.warehouseName !== null) ? importMaterialDetail.warehouseName : '- -' }}
            </td>
            <td>
              {{ (importMaterialDetail.expireDate !== null) ? importMaterialDetail.expireDate : '- -' }}
            </td>
          </tr>
          </tbody>
        </table>
      </div>

    </div>
  </b-modal>
</template>

<script>

  import {
    check_null, number_with_commas,insertCommasDecimal
  } from "../../../../static";

  export default {
    name: 'BackendInventoryImportMaterialDetail',
    data() {
      return {
      }
    },
    props: ['importMaterialDetail'],
    created() {
    },
    methods: {
      _handleCloseModal() {
        this.$bvModal.hide('inventory_material-detail');
      },
      formatNumber(number) {
        return number_with_commas(number);
      },
      check_null(index) {
        return check_null(index);
      },
      insertCommasDecimal
    }
  }
</script>
