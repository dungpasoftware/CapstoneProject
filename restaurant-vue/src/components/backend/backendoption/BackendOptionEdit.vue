<template>
  <div class="dish-addnew animate__animated animate__fadeIn animate__faster">
    <div class="an__title">
      <i class="fad fa-pizza"></i>
      Chỉnh sửa Topping
    </div>
    <form @submit.prevent="_handleSaveButtonClick" class="an-body" v-if="optionData !== null">
      <div class="an-form">
        <div class="an-item">
          <label>Tên <span class="starr">*</span></label>
          <input :maxlength="100" v-model="optionData.optionName">
        </div>
        <div class="an-item">
          <label class="in-select">Hình thức</label>
          <select v-model="optionData.optionType">
            <option :value="'MONEY'">Thêm tiền</option>
            <option :value="'ADD'">Không tính tiền</option>
          </select>
        </div>
        <div class="an-item">
          <label>Đơn vị <span class="starr">*</span></label>
          <input :maxlength="50" v-model="optionData.unit">
        </div>
        <div class="an-item">
          <label>Giá nguyên vật liệu</label>
          <input disabled v-mask="mask_number" v-model="optionData.cost">
        </div>
        <div class="an-item" v-if="optionData.optionType === 'MONEY'">
          <label>Giá thành phẩm</label>
          <input v-mask="mask_number" v-model="optionData.optionCost">
        </div>
        <div class="an-item" v-if="optionData.optionType === 'MONEY'">
          <label>Giá bán</label>
          <input v-mask="mask_number" v-model="optionData.price">
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
                  {{ (optionQ.material.unitPrice !== null) ? number_with_commas(optionQ.material.unitPrice) : ''}}đ
                  /
                  {{ (optionQ.material.unit !== null) ? optionQ.material.unit : '' }}
                </template>
              </td>
              <td>
                <div v-if="optionData.materialId !== 0" style="width: 100%; display: flex; align-items: center">
                  <input v-mask="mask_decimal" class="mr-1" v-model="optionQ.quantity"
                         @keyup="_handleMaterialQuantityChange(key)">
                  ({{ (optionQ.material !== null && optionQ.material.unit !== null) ? optionQ.material.unit : '' }})
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
        <button type="submit" class="an-submit__save">
          Lưu
        </button>
      </div>
    </form>
  </div>
</template>

<script>
  import {
    check_number,
    check_null,
    number_with_commas,
    mask_number,
    mask_decimal,
    remove_hyphen
  } from "../../../static";

  export default {
    data() {
      return {
        optionId: this.$route.params.id,
        optionData: null,
        materials: null,
        formError: {
          list: [],
          isShow: false
        },
        mask_number,
        mask_decimal,
      };
    },
    created() {
      this.$store.dispatch('getOptionById', this.optionId)
        .then(response => {
          console.log(response.data)
          response.data.quantifierOptions.map(item => {
            item['materialId'] = item.material.materialId;
            return item;
          })
          this.optionData = response.data;
        }).catch(err => {
        console.error(err);
      })
      this.$store.dispatch('getAllMaterial')
        .then(({data}) => {
          this.materials = data;
        }).catch(error => {
        console.log(error)
      })
    },
    methods: {
      number_with_commas,
      sumQuantifierCost() {
        this.optionData.cost = 0;
        this.optionData.cost = this.optionData.quantifierOptions.reduce((sum, addItem) => {
          return sum += (addItem.cost > 0) ? addItem.cost : 0;
        }, 0);
        this.optionData.optionCost = (this.optionData.optionType === 'MONEY') ? (this.optionData.cost * 2) : 0;
      },
      _handlePhoneChange(e) {
        return check_number(e);
      },
      _handleAddNewQuantifier() {
        this.optionData.quantifierOptions.push({
          quantifierOptionId: null,
          materialId: null,
          quantity: null,
          description: null,
          material: null,
          cost: null
        })
      },
      _handleMaterialSelect(key) {
        let materialId = this.optionData.quantifierOptions[key].materialId;
        console.log(this.materials)
        this.materials.forEach(material => {

          if (material.materialId == materialId) {
            this.optionData.quantifierOptions[key].material = material;
          }
        })
      },
      _handleMaterialQuantityChange(key) {
        this.optionData.quantifierOptions[key].cost =
          this.optionData.quantifierOptions[key].material.unitPrice *
          remove_hyphen(this.optionData.quantifierOptions[key].quantity);
        this.optionData.quantifierOptions[key].cost = Math.floor(this.optionData.quantifierOptions[key].cost);
        this.sumQuantifierCost();
      },
      _handleMaterialDelete(key) {
        this.optionData.quantifierOptions.splice(key, 1);
        this.sumQuantifierCost();
      },
      _handleSaveButtonClick() {
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
        if (this.optionData.optionType === 'MONEY') {
          if (check_null(this.optionData.price)) {
            this.formError.list.push('Giá bán không được để trống');
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
            optionId: this.optionData.optionId,
            optionName: this.optionData.optionName,
            optionType: this.optionData.optionType,
            unit: this.optionData.unit,
            cost: parseFloat(remove_hyphen(this.optionData.cost)),
            optionCost: (this.optionData.optionType === 'MONEY') ? parseFloat(remove_hyphen(this.optionData.optionCost)) : 0,
            price: (this.optionData.optionType === 'MONEY') ? parseFloat(remove_hyphen(this.optionData.price)) : 0,
            quantifierOptions: []
          }
          this.optionData.quantifierOptions.forEach(item => [
            optionDataRequest.quantifierOptions.push({
              quantifierOptionId: null,
              materialId: item.material.materialId,
              cost: item.cost,
              quantity: parseFloat(remove_hyphen(item.quantity)),
              description: item.description
            })
          ])
          this.$store.dispatch('editOptionById', optionDataRequest)
            .then(response => {
              this.$swal('Thành công!',
                'Topping đã được cập nhật lên hệ thống.',
                'success').then((result) => {
                if (result.value) {
                  this.$router.push({name: 'backend-option'})
                }
              })
            }).catch(err => {
            this.formError.list.push(err.message);
            this.formError.isShow = true;
          })
        }
      }
    }
  }
</script>
