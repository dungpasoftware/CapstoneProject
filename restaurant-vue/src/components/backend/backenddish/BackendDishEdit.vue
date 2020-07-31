<template>
  <div class="dish-addnew animate__animated animate__fadeIn animate__faster">
    <div class="an__title">
      <i class="fad fa-pizza"></i>
      Chỉnh sửa món
    </div>
    <div class="an-body" v-if="dishData !== null">
      <div class="an-form">
        <div class="an-item">
          <label>Mã thực đơn</label>
          <input disabled v-model="dishData.dishCode">
        </div>
        <div class="an-item">
          <label>Tên thực đơn <span class="starr">*</span></label>
          <input required v-model="dishData.dishName" v-on:input="_handleDishNameChange">
        </div>
        <div class="an-item">
          <label>Đơn vị</label>
          <input v-model="dishData.dishUnit">
        </div>
        <div class="an-item">
          <label>Giá nhập <span class="starr">*</span></label>
          <input v-mask="mask_number" v-model="dishData.cost">
        </div>
        <div class="an-item">
          <label>Giá thành phẩm</label>
          <input disabled v-mask="mask_number" v-model="dishData.dishCost">
        </div>
        <div class="an-item">
          <label>Giá bán <span class="starr">*</span></label>
          <input v-mask="mask_number" v-model="dishData.defaultPrice">
        </div>
        <div class="an-item">
          <label>Thời gian hoàn thành ước tính (phút)</label>
          <input v-mask="mask_number" v-model="dishData.timeComplete">
        </div>
        <div class="an-item">
          <label>Thời gian thông báo (phút)</label>
          <input v-mask="mask_number" v-model="dishData.timeNotification">
        </div>
        <div class="an-item">
          <label class="in-select">Loại sản phẩm</label>
          <select :defaultvalue="false" v-model="dishData.typeReturn">
            <option :value="false">Không thể trả lại</option>
            <option :value="true">Có thể trả lại</option>
          </select>
        </div>
        <div class="an-item">
          <label>Nhóm thực đơn</label>
          <div class="an-item-select">
            <div class="dropdown">
              <button type="button" class="an-item-select__button" data-toggle="dropdown">
                <i class="fal fa-plus"></i>
              </button>
              <div class="dropdown-menu">
                <template v-if="categories !== null">
                  <div v-for="(category, key, index) in categories" :key="index"
                       @click="_handleCategoryClick(category)"
                       class="dropdown-item">
                    {{ (category.categoryName !== null) ? category.categoryName : '' }}
                  </div>
                </template>
              </div>
            </div>
            <ul v-if="dishData.categories.length > 0" class="an-item-select__list">
              <li v-for="(catcheck, key, index) in dishData.categories" :key="key">
                {{ (catcheck.categoryName !== null) ? catcheck.categoryName : '' }}
                <span class="remove" @click="_handleCategoryDelete(key)">
                  <i class="fad fa-times-circle"></i>
                </span>
              </li>
            </ul>
          </div>
        </div>
        <div class="an-item">
          <label>Option</label>
          <div class="an-item-select">
            <div class="dropdown">
              <button type="button" class="an-item-select__button" data-toggle="dropdown">
                <i class="fal fa-plus"></i>
              </button>
              <div class="dropdown-menu">
                <template v-if="options !== null && options.length > 0">
                  <div v-for="(option, key, index) in options" :key="index"
                       @click="_handleOptionClick(option)"
                       class="dropdown-item">
                    {{ (option.optionName !== null) ? option.optionName : '' }}
                  </div>
                </template>
              </div>
            </div>
            <ul v-if="dishData.options.length > 0" class="an-item-select__list">
              <li v-for="(opcheck, key, index) in dishData.options" :key="key">
                {{ (opcheck.optionName !== null) ? opcheck.optionName : '' }}
                <span class="remove" @click="_handleOptionDelete(key)">
                  <i class="fad fa-times-circle"></i>
                </span>
              </li>
            </ul>
          </div>
        </div>
        <div class="an-item">
          <label>Mô tả cho món ăn</label>
          <textarea v-model="dishData.description" rows="3">
          </textarea>
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
          <template v-if="dishData.quantifiers.length > 0">
            <tr v-for="(dishMas, key) in dishData.quantifiers" :key="key">
              <td>
              </td>
              <td>
                <select v-model="dishMas.material.materialId"
                        @change="_handleMaterialSelectChange(key, dishMas.material.materialId)"
                        v-if="quantifiers !== null && quantifiers.length > 0">
                  <option disabled selected :value="null">Chọn tên nguyên vật liệu</option>
                  <option v-for="(material, selectKey) in quantifiers"
                          :key="selectKey"
                          :value="material.materialId">
                    {{material.materialName}}
                  </option>
                </select>
              </td>
              <td>
                <template v-if="dishMas.material !== null && dishMas.material.materialId !== 0">
                  {{ (dishMas.material.unitPrice !== null) ? convert_number(dishMas.material.unitPrice) : 0 }}
                  đ /
                  {{ (dishMas.material.unit !== null) ? dishMas.material.unit : '' }}
                </template>
              </td>
              <td>
                <div v-if="dishMas.material.materialId !== 0" style="width: 100%; display: flex; align-items: center">
                  <input class="textalign-right mr-1" v-model="dishMas.quantity"
                         v-mask="mask_decimal"
                         @keyup="_handleMaterialUnitPrice(key, dishMas.material.unitPrice, dishMas.quantity)">
                  ({{dishMas.material.unit}})
                </div>
              </td>
              <td>
                {{ (dishMas.cost !== null) ? dishMas.cost : '' }}đ
              </td>
              <td>
                <textarea v-model="dishMas.description"></textarea>
              </td>
              <td>
                <button @click="_handleMaterialDelete(key, dishMas)"
                        class="btn-default-green btn-red btn-xs">Xoá
                </button>
              </td>
            </tr>
          </template>
          <tr>
            <td>
              <span class="add-new" @click="_handleMaterialAddNew"><i class="fad fa-plus-circle"></i></span>
            </td>
            <td colspan="6"></td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="an-submit">
        <router-link tag="button" class="an-submit__cancel" :to="{name: 'backend-dish'}">
          Huỷ
        </router-link>
        <button class="an-submit__save" @click="_handleSaveButtonClick">
          Lưu
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    convert_code,
    check_number,
    check_null,
    mask_number,
    mask_decimal,
    number_with_commas,
    remove_hyphen,
  } from "../../../static";

  export default {
    data() {
      return {
        dishId: this.$route.params.id,
        dishData: null,
        categories: null,
        options: null,
        quantifiers: [],
        mask_decimal,
        mask_number
      };
    },
    created() {
      this.$store.dispatch('getDishById', this.dishId)
        .then(response => {
          console.log(response.data)
          this.dishData = response.data;
        }).catch(err => {
        console.error(err);
      })
      this.$store.dispatch('getAllCategories')
        .then(({data}) => {
          this.categories = data;
        }).catch(error => {
        console.log(error)
      })
      this.$store.dispatch('getAllOptions')
        .then(({data}) => {
          this.options = data;
        }).catch(error => {
        console.log(error)
      })
      this.$store.dispatch('getAllMaterial')
        .then(({data}) => {
          this.quantifiers = data;
        }).catch(error => {
        console.log(error)
      })
    },
    methods: {
      convert_number(x) {
        return number_with_commas(x);
      },
      _handleDishNameChange() {
        this.dishData.dishCode = convert_code(this.dishData.dishName);
      },
      _handleCategoryClick(category) {
        let canAdd = true;
        if (this.dishData.categories.length > 0) {
          this.dishData.categories.forEach(item => {
            if (item.categoryId === category.categoryId) canAdd = false;
          })
        }
        if (canAdd) this.dishData.categories.push(category);
      },
      _handleCategoryDelete(key) {
        this.dishData.categories.splice(key, 1);
      },
      _handleOptionClick(option) {
        let canAdd = true;
        if (this.dishData.options.length > 0) {
          this.dishData.options.forEach(item => {
            if (item.optionId === option.optionId) canAdd = false;
          })
        }
        if (canAdd) this.dishData.options.push(option);
      },
      _handleOptionDelete(key) {
        this.dishData.options.splice(key, 1);
      },
      _handleMaterialAddNew() {
        this.dishData.quantifiers.push({
          cost: null,
          description: null,
          quantity: null,
          unit: null,
          material: {
            materialId: null,
            materialName: null,
            unit: null,
            unitPrice: null,
          }
        })
      },
      _handleMaterialSelectChange(key, materialKey) {
        materialKey = materialKey - 1;
        this.dishData.quantifiers[key].material.materialId = this.quantifiers[materialKey].materialId;
        this.dishData.quantifiers[key].material.unit = this.quantifiers[materialKey].unit;
        this.dishData.quantifiers[key].material.unitPrice = this.quantifiers[materialKey].unitPrice;
      },
      _handleMaterialUnitPrice(key, unitPrice, quantity) {
        this.dishData.quantifiers[key].cost = Math.floor(unitPrice * remove_hyphen(quantity));
        this.dishData.cost = 0;
        this.dishData.cost = this.dishData.quantifiers.reduce((accumulator, currentValue) => {
          return accumulator += currentValue.cost
        }, 0)
        this.dishData.dishCost = this.dishData.cost * 2
      },
      _handleMaterialDelete(key, data) {
        this.dishData.quantifiers.splice(key, 1);
        this.dishData.cost = 0;
        this.dishData.cost = this.dishData.quantifiers.reduce((accumulator, currentValue) => {
          return accumulator += currentValue.cost
        }, 0)
        this.dishData.dishCost = this.dishData.cost * 2
      },
      _handleSaveButtonClick() {
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(this.dishData.dishName)) {
          this.formError.list.push('Tên thực đơn không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.dishData.cost)) {
          this.formError.list.push('Giá nhập không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.dishData.defaultPrice)) {
          this.formError.list.push('Giá bán không được để trống');
          this.formError.isShow = true;
        }

        if (!this.formError.isShow) {

          let dishEditRequest = {
            dishId: this.dishId,
            dishCode: this.dishData.dishCode,
            dishName: this.dishData.dishName,
            dishUnit: this.dishData.dishUnit,
            defaultPrice: remove_hyphen(this.dishData.defaultPrice),
            cost: remove_hyphen(this.dishData.cost),
            dishCost: remove_hyphen(this.dishData.dishCost),
            description: this.dishData.description,
            timeComplete: remove_hyphen(this.dishData.timeComplete),
            timeNotification: remove_hyphen(this.dishData.timeNotification),
            imageUrl: this.dishData.imageUrl,
            typeReturn: this.dishData.typeReturn,
            categoryIds: [],
            optionIds: [],
            quantifiers: []
          }
          this.dishData.categories.forEach(item => {
            dishEditRequest.categoryIds.push(item.categoryId)
          })
          this.dishData.options.forEach(item => {
            dishEditRequest.optionIds.push(item.optionId)
          })
          this.dishData.quantifiers.forEach(item => {
            dishEditRequest.quantifiers.push({
              materialId: item.material.materialId,
              quantity: remove_hyphen(item.quantity),
              unit: item.material.unit,
              cost: item.cost,
              description: item.description
            })
          })
          console.log(dishEditRequest)
          this.$store.dispatch('editDishById', dishEditRequest)
            .then(response => {
              this.$swal(`Chỉnh sửa thành công`,
                'Danh sách thực đơn đã được cập nhật lên hệ thống.',
                'success').then(result => {
                if (result.value) {
                  this.$router.push({name: 'backend-dish'});
                }
              })
            }).catch(error => {
            console.error(error)
            this.formError.list.push(error.message);
            this.formError.isShow = true;
          });
        }
      }
    }
  }
</script>
