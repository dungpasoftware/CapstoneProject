<template>
  <div class="dish-addnew animate__animated animate__fadeIn animate__faster">
    <div class="an__title">
      <i class="fad fa-pizza"/>
      Thêm món mới
    </div>
    <div class="an-body">
      <div class="an-form">
        <div class="an-item">
          <label>Mã thực đơn</label>
          <input disabled v-model="dishData.dishCode">
        </div>
        <div class="an-item">
          <label>Tên thực đơn <span class="starr">*</span></label>
          <input v-model="dishData.dishName" v-on:input="_handleDishNameChange" :maxlength="150">
        </div>
        <div class="an-item">
          <label>Đơn vị <span class="starr">*</span></label>
          <input v-model="dishData.dishUnit" :maxlength="50">
        </div>
        <div class="an-item">
          <label>Giá nguyên vật liệu</label>
          <input disabled v-mask="mask_number_limit(20)" v-model="dishData.cost">
        </div>
        <div class="an-item">
          <label>Giá thành phẩm <span class="starr">*</span></label>
          <input v-mask="mask_number_limit(20)" v-model="dishData.dishCost">
        </div>
        <div class="an-item">
          <label>Giá bán <span class="starr">*</span></label>
          <input v-mask="mask_number_limit(20)" v-model="dishData.defaultPrice">
        </div>
        <div class="an-item">
          <label>Thời gian hoàn thành ước tính (phút)</label>
          <input v-mask="mask_number_limit(5)" v-model="dishData.timeComplete">
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
                  <div v-for="(category, key) in categories" :key="key"
                       @click="_handleCategoryClick(category)"
                       class="dropdown-item">
                    {{ (category.categoryName !== null) ? category.categoryName : '' }}
                  </div>
                </template>
              </div>
            </div>
            <ul v-if="dishData.categories.length > 0" class="an-item-select__list">
              <li v-for="(catcheck, key) in dishData.categories" :key="key">
                {{ (catcheck.categoryName !== null) ? catcheck.categoryName : '' }}
                <span class="remove" @click="_handleCategoryDelete(key)">
                  <i class="fad fa-times-circle"></i>
                </span>
              </li>
            </ul>
          </div>
        </div>
        <div class="an-item">
          <label>Toppings</label>
          <div class="an-item-select">
            <div class="dropdown">
              <button type="button" class="an-item-select__button" data-toggle="dropdown">
                <i class="fal fa-plus"></i>
              </button>
              <div class="dropdown-menu">
                <template v-if="options !== null">
                  <div v-for="(option, key, index) in options" :key="index"
                       @click="_handleOptionClick(option)"
                       class="dropdown-item">
                    {{ (option.optionName !== null) ? option.optionName : '' }}
                  </div>
                </template>
              </div>
            </div>
            <ul v-if="dishData.options.length > 0" class="an-item-select__list">
              <li v-for="(opcheck, key) in dishData.options" :key="key">
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
          <textarea v-model="dishData.description" rows="3" :maxlength="200">
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
                <select v-model="dishMas.materialId"
                        @change="_handleMaterialSelectChange(key, dishMas.materialId)"
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
                <template v-if="dishMas.materialId !== 0">
                  {{(dishMas.unitPrice !== null) ? number_with_commas(Math.ceil(dishMas.unitPrice)) : 0 }}đ / {{dishMas.unit}}
                </template>
              </td>
              <td>
                <div v-if="dishMas.materialId !== null" style="width: 100%; display: flex; align-items: center">
                  <input type="text" class="textalign-right mr-1" v-model="dishMas.quantity"
                         v-mask="mask_decimal_limit(5)"
                         @keyup="_handleMaterialUnitPrice(key, dishMas.unitPrice, dishMas.quantity)">
                  ({{dishMas.unit}})
                </div>
              </td>
              <td>
                {{(dishMas.cost !== null) ? number_with_commas(dishMas.cost) : 0}}đ
              </td>
              <td>
                <textarea v-model="dishMas.description"></textarea>
              </td>
              <td>
                <button type="button" @click="_handleMaterialDelete(key)"
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
      <b-alert class="mt-4" v-model="formError.isShow" variant="danger" dismissible>
        <ul class="mb-0" v-if="formError.list.length > 0">
          <li v-for="(item, key) in formError.list" :key="key">
            {{item}}
          </li>
        </ul>
      </b-alert>
      <div class="an-submit">
        <router-link tag="button" type="button" class="an-submit__cancel" :to="{name: 'backend-dish'}">
          Huỷ
        </router-link>
        <button class="an-submit__save" @click="_handleSaveButtonClick">
          Tạo mới
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    convert_code,
    check_null,
    mask_number,
    mask_number_limit,
    mask_decimal,
    mask_decimal_limit,
    number_with_commas,
    remove_hyphen,
    isLostConnect
  } from "../../../static";

  export default {
    data() {
      return {
        dishData: {
          dishCode: null, //Required
          dishName: null, //Required
          dishUnit: null, //Required
          defaultPrice: null, //Required
          cost: null, //Required
          dishCost: null,
          description: null,
          timeComplete: null,
          imageUrl: null,
          typeReturn: false,
          categories: [],
          options: [],
          quantifiers: [],
        },
        categories: null,
        options: null,
        quantifiers: null,
        formError: {
          list: [],
          isShow: false
        },
        mask_number,
        mask_number_limit,
        mask_decimal,
        mask_decimal_limit,
      };
    },
    created() {
      this.initCategories();
    },
    methods: {
      number_with_commas,
      initCategories() {
        this.$store.dispatch('getAllCategories')
          .then(({data}) => {
            this.categories = data;
            this.initOptions();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
      },
      initOptions() {
        this.$store.dispatch('getAllOptions')
          .then(({data}) => {
            this.options = data;
            this.initMaterials();
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
      },
      initMaterials() {
        this.$store.dispatch('getAllMaterial')
          .then(({data}) => {
            this.quantifiers = data;
          }).catch(error => {
          if (!isLostConnect(error)) {

          }
        })
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
          materialId: null,
          unit: null,
          unitPrice: null,
          quantity: null,
          cost: null,
          description: null
        })
      },
      _handleMaterialSelectChange(key, materialKey) {
        materialKey = materialKey - 1;
        this.dishData.quantifiers[key].materialId = this.quantifiers[materialKey].materialId;
        this.dishData.quantifiers[key].unit = this.quantifiers[materialKey].unit;
        this.dishData.quantifiers[key].unitPrice = this.quantifiers[materialKey].unitPrice;
        this._handleMaterialUnitPrice(key,
          this.dishData.quantifiers[key].unitPrice,
          this.dishData.quantifiers[key].quantity ? this.dishData.quantifiers[key].quantity : '0');
      },
      _handleMaterialUnitPrice(key, unitPrice = 0, quantity = '0') {
        this.dishData.quantifiers[key].cost = Math.ceil(unitPrice * remove_hyphen(quantity));
        this.dishData.cost = 0;
        this.dishData.cost = this.dishData.quantifiers.reduce((accumulator, currentValue) => {
          return accumulator += (currentValue.cost > 0) ? currentValue.cost : 0;
        }, 0)
        this.dishData.dishCost = this.dishData.cost * 2
      },

      _handleMaterialDelete(key) {
        this.dishData.quantifiers.splice(key, 1);
        this.dishData.cost = 0;
        this.dishData.cost = this.dishData.quantifiers.reduce((accumulator, currentValue) => {
          return accumulator += currentValue.cost
        }, 0)
        this.dishData.dishCost = this.dishData.cost * 2
      },

      _handleSaveButtonClick() {
        event.preventDefault();
        this.formError = {
          list: [],
          isShow: false
        }
        if (check_null(this.dishData.dishName)) {
          this.formError.list.push('Tên thực đơn không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.dishData.dishUnit)) {
          this.formError.list.push('Đơn vị không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.dishData.dishCost) || this.dishData.dishCost <= 0) {
          this.formError.list.push('Giá thành phẩm không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.dishData.defaultPrice) || this.dishData.defaultPrice <= 0) {
          this.formError.list.push('Giá bán không được để trống');
          this.formError.isShow = true;
        }
        if (check_null(this.dishData.quantifiers) || this.dishData.quantifiers.length === 0) {
          this.formError.list.push('Nguyên vật liệu không được để trống');
          this.formError.isShow = true;
        }
        this.dishData.quantifiers.forEach((item, key) => {
          if (item.materialId === null) {
            this.formError.list.push(`Nguyên vật liệu ${key + 1} không được để trống`);
            this.formError.isShow = true;
          }
        })

        if (!this.formError.isShow) {
          let dishRequest = {
            dishCode: !check_null(this.dishData.dishCode) ? this.dishData.dishCode : '',
            dishName: !check_null(this.dishData.dishName) ? this.dishData.dishName : '',
            dishUnit: !check_null(this.dishData.dishUnit) ? this.dishData.dishUnit : '',
            defaultPrice: !check_null(this.dishData.defaultPrice) ? parseFloat(remove_hyphen(this.dishData.defaultPrice)) : 0,
            cost: !check_null(this.dishData.cost) ? parseFloat(remove_hyphen(this.dishData.cost)) : 0,
            dishCost: !check_null(this.dishData.dishCost) ? parseFloat(remove_hyphen(this.dishData.dishCost)) : 0,
            description: !check_null(this.dishData.description) ? this.dishData.description : '',
            timeComplete: !check_null(this.dishData.timeComplete) ? parseFloat(remove_hyphen(this.dishData.timeComplete)) : 0,
            imageUrl: !check_null(this.dishData.imageUrl) ? this.dishData.imageUrl : '',
            typeReturn: this.dishData.typeReturn,
            categoryIds: [],
            optionIds: [],
            quantifiers: []
          }
          this.dishData.categories.forEach(item => {
            dishRequest.categoryIds.push(item.categoryId)
          })
          this.dishData.options.forEach(item => {
            dishRequest.optionIds.push(item.optionId)
          })
          this.dishData.quantifiers.forEach(item => {
            dishRequest.quantifiers.push({
              materialId: item.materialId,
              quantity: !check_null(item.quantity) ? parseFloat(remove_hyphen(item.quantity)) : 0,
              cost: item.cost,
              description: !check_null(item.description) ? item.description : ''
            })
          })
          this.$store.dispatch('addNewDish', dishRequest)
            .then(response => {
              this.$swal(`Tạo mới thành công`,
                'Danh sách thực đơn đã được cập nhật lên hệ thống.',
                'success').then(result => {
                if (result.value) {
                  this.$router.push({name: 'backend-dish'});
                }
              })
            }).catch(error => {
            if (!isLostConnect(error, false)) {
              error.response.data.messages.map(err => {
                this.formError.list.push(err);
                this.formError.isShow = true;
              })
            }
          });
        }
      }
    }
  }
</script>
