<template>
  <div class="dish-addnew animate__animated animate__fadeIn animate__faster">
    <div class="an__title">
      <i class="fad fa-pizza"/>
      Thêm Topping
    </div>
    <div class="an-body">
      <div class="an-form">
        <div class="an-item">
          <label>Tên <span class="starr">*</span></label>
          <input v-model="optionData.optionName" :maxlength="100">
        </div>
        <div class="an-item">
          <label class="in-select">Hình thức</label>
          <select :defaultvalue="'MONEY'" v-model="optionData.optionType">
            <option :value="'MONEY'">Thêm tiền</option>
            <option :value="'ADD'">Không tính tiền</option>
          </select>
        </div>
        <div class="an-item">
          <label>Đơn vị <span class="starr">*</span></label>
          <input v-model="optionData.unit" :maxlength="50">
        </div>
        <div class="an-item">
          <label>Giá nguyên vật liệu</label>
          <input disabled v-mask="mask_number_limit(20)" v-model="optionData.cost">
        </div>
        <div class="an-item" v-if="optionData.optionType === 'MONEY'">
          <label>Giá thành phẩm <span class="starr">*</span></label>
          <input v-mask="mask_number_limit(20)" v-model="optionData.optionCost">
        </div>
        <div class="an-item" v-if="optionData.optionType === 'MONEY'">
          <label>Giá bán <span class="starr">*</span></label>
          <input v-mask="mask_number_limit(20)" v-model="optionData.price">
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
            <th>Mô tả</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <template v-if="optionData.quantifierOptions.length > 0">
            <tr v-for="(optionQ, key) in optionData.quantifierOptions" :key="key">
              <td>
              </td>
              <td>
                <select v-model="optionQ.materialId"
                        @change="_handleMaterialSelect(key)"
                        v-if="materials !== null && materials.length > 0">
                  <option disabled selected :value="null">Chọn tên nguyên vật liệu</option>
                  <option v-for="(material, materialKey) in materials"
                          :key="materialKey"
                          :value="material.materialId">
                    {{material.materialName}}
                  </option>
                </select>
              </td>
              <td>
                <template v-if="optionQ.material !== null">
                  {{ (optionQ.material.unitPrice !== null) ? number_with_commas(Math.ceil(optionQ.material.unitPrice)) : ''}}đ
                  /
                  <span class="default-text">
                  {{ (optionQ.material.unit !== null) ? optionQ.material.unit : '' }}
                  </span>
                </template>
              </td>
              <td>
                <div v-if="optionQ.materialId !== null" style="width: 100%; display: flex; align-items: center">
                  <input v-mask="mask_decimal_limit(5)" style="width: 85%" class="textalign-right mr-1" v-model="optionQ.quantity"
                         @keyup="_handleMaterialQuantityChange(key)">
                  <div style="word-break: break-word">
                    ({{ (optionQ.material !== null && optionQ.material.unit !== null) ? optionQ.material.unit : '' }})
                  </div>
                </div>
              </td>
              <td>
                {{ (optionQ.cost !== null) ? number_with_commas(optionQ.cost) : 0 }}đ
              </td>
              <td>
                <textarea :maxlength="200" v-model="optionQ.description"></textarea>
              </td>
              <td>
                <button @click="_handleMaterialDelete(key)"
                        class="btn-default-green btn-red btn-xs">
                  Xoá
                </button>
              </td>
            </tr>
          </template>
          <tr>
            <td>
              <span class="add-new" @click="_handleAddNewQuantifier"><i class="fad fa-plus-circle"></i></span>
            </td>
            <td colspan="6"></td>
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
        <router-link tag="button" type="button" class="an-submit__cancel" :to="{name: 'backend-option'}">
          Huỷ
        </router-link>
        <button type="button" class="an-submit__save" @click="_handleSaveButtonClick">
          Tạo mới
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    check_number,
    check_null,
    number_with_commas,
    mask_number,
    mask_decimal,
    mask_decimal_limit,
    mask_number_limit,
    remove_hyphen, isLostConnect
  } from "../../../static";

  export default {
    data() {
      return {
        optionData: {
          optionName: null,
          optionType: 'MONEY',
          unit: null,
          cost: null,
          optionCost: null,
          price: null,
          quantifierOptions: []
        },
        materials: null,
        formError: {
          list: [],
          isShow: false
        },
        mask_number,
        mask_decimal,
        mask_decimal_limit,
        mask_number_limit,
      };
    },
    created() {
      this.initMaterials();
    },
    methods: {
      number_with_commas,
      initMaterials() {
        this.$store.dispatch('openLoader');
        this.$store.dispatch('getAllMaterial')
          .then(({data}) => {
            this.materials = data;
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        }).finally(() => {
          this.$store.dispatch('closeLoader');
        })
      },
      sumQuantifierCost() {
        this.optionData.cost = 0;
        this.optionData.cost = this.optionData.quantifierOptions.reduce((sum, addItem) => {
          return sum += (addItem.cost > 0) ? addItem.cost : 0;
        }, 0);
      },
      _handlePhoneChange(e) {
        return check_number(e);
      },
      _handleAddNewQuantifier() {
        this.optionData.quantifierOptions.push({
          quantifierOptionId: null,
          materialId: null,
          quantity: '',
          description: null,
          material: null,
          cost: null
        })
      },
      _handleMaterialSelect(key) {
        let materialId = this.optionData.quantifierOptions[key].materialId;
        this.materials.forEach(material => {
          if (material.materialId == materialId) {
            this.optionData.quantifierOptions[key].material = material;
            this._handleMaterialQuantityChange(key);
          }
        })
      },
      _handleMaterialQuantityChange(key) {
        this.optionData.quantifierOptions[key].cost =
          this.optionData.quantifierOptions[key].material.unitPrice *
          remove_hyphen(this.optionData.quantifierOptions[key].quantity);
        this.optionData.quantifierOptions[key].cost = Math.ceil(this.optionData.quantifierOptions[key].cost);
        this.sumQuantifierCost();
      },
      _handleMaterialDelete(key) {
        this.optionData.quantifierOptions.splice(key, 1);
        this.sumQuantifierCost();
      },
      _handleSaveButtonClick() {
        if (this.$store.getters.getLoader) {
          this.$swal({
            position: 'top-end',
            icon: 'warning',
            title: 'Đừng thao tác quá nhanh',
            showConfirmButton: false,
            timer: 5000,
            toast: true,
          });
        } else {
          this.formError = {
            list: [],
            isShow: false
          }
          if (check_null(this.optionData.optionName)) {
            this.formError.list.push('Tên topping không được để trống');
            this.formError.isShow = true;
          }
          if (check_null(this.optionData.unit)) {
            this.formError.list.push('Đơn vị không được để trống');
            this.formError.isShow = true;
          }
          if (check_null(this.optionData.cost)) {
            this.formError.list.push('Giá nguyên vật liệu không được để trống');
            this.formError.isShow = true;
          } else if (this.optionData.cost <= 0) {
            this.formError.list.push('Giá nguyên vật liệu phải lớn hơn 0');
            this.formError.isShow = true;
          }
          if (this.optionData.optionType === 'MONEY') {
            if (check_null(this.optionData.optionCost)) {
              this.formError.list.push('Giá thành phẩm không được để trống');
              this.formError.isShow = true;
            } else if (this.optionData.optionCost <= 0) {
              this.formError.list.push('Giá thành phẩm phải lớn hơn 0');
              this.formError.isShow = true;
            }
            if (check_null(this.optionData.price)) {
              this.formError.list.push('Giá bán không được để trống');
              this.formError.isShow = true;
            } else if (this.optionData.price <= 0) {
              this.formError.list.push('Giá bán phải lớn hơn 0');
              this.formError.isShow = true;
            }
          }
          this.optionData.quantifierOptions.forEach((item, key) => {
            if (item.material === null) {
              this.formError.list.push(`Nguyên vật liệu ${key + 1} không được để trống`);
              this.formError.isShow = true;
            }
          })

          if (!this.formError.isShow) {
            let optionDataRequest = {
              optionName: !check_null(this.optionData.optionName) ? this.optionData.optionName : '',
              optionType: this.optionData.optionType,
              unit: !check_null(this.optionData.unit) ? this.optionData.unit : '',
              cost: !check_null(this.optionData.cost) ? parseFloat(remove_hyphen(this.optionData.cost)) : 0,
              optionCost: (!check_null(this.optionData.optionCost) && this.optionData.optionType === 'MONEY') ? parseFloat(remove_hyphen(this.optionData.optionCost)) : 0,
              price: (!check_null(this.optionData.price) && this.optionData.optionType === 'MONEY') ? parseFloat(remove_hyphen(this.optionData.price)) : 0,
              quantifierOptions: []
            }
            this.optionData.quantifierOptions.forEach(item => [
              optionDataRequest.quantifierOptions.push({
                quantifierOptionId: null,
                materialId: item.material.materialId,
                cost: !check_null(item.cost) ? item.cost : 0,
                quantity: !check_null(item.quantity) ? parseFloat(remove_hyphen(item.quantity)) : 0,
                description: !check_null(item.description) ? item.description : ''
              })
            ])
            console.log(optionDataRequest)
            this.$store.dispatch('openLoader');
            this.$store.dispatch('addNewOption', optionDataRequest)
              .then(response => {
                this.$swal('Thành công!',
                  'Topping mới đã được cập nhật lên hệ thống.',
                  'success').then((result) => {
                  this.$router.push({name: 'backend-option'})
                })
              }).catch(error => {
                console.log(error.response);
              if (!isLostConnect(error, false)) {
                this.$swal({
                  title: 'Có lỗi xảy ra',
                  html: 'Vui lòng thử lại',
                  icon: 'warning',
                  showCloseButton: true,
                  confirmButtonText: 'Đóng',
                });
              }
            }).finally(() => {
              this.$store.dispatch('closeLoader');
            })
          }
        }
      }
    }
  }
</script>
