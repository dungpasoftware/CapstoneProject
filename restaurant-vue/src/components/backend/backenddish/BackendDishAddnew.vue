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
          <input v-mask="mask_number_limit(20)" v-model="dishData.dishCost" @keyup="_handleDishCodeInput">
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
          <select v-model="dishData.typeReturn">
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
        <div class="an-item">
          <label>Hình ảnh của món ăn</label>
          <input hidden id="dish_addnew_image" type="file" accept="image/*" @change="_handleInputImageChange">
          <div v-if="!imageUploading" class="an-item__image"
                 :style="{'background-image': `url(${(dishData.imageUrl) ? dishData.imageUrl : ''})`}">
            <label for="dish_addnew_image" :class="['an-item__image--inner', (dishData.imageUrl) ? 'active' : '']">
              <i class="fad fa-image-polaroid active"></i>
              <span>Chọn ảnh</span>
            </label>
            <button @click="_handleButtonRemoveImage" type="button"
              v-b-popover.hover.right="'Xoá ảnh'" v-if="dishData.imageUrl" class="an-item__image--delete">
              <i class="fas fa-trash-alt"></i>
            </button>
          </div>
          <div v-else class="an-item__image"
                 :style="{'background-image': `url(${(dishData.imageUrl) ? dishData.imageUrl : ''})`}">
            <div class="an-item__image--uploading">
              <div class="lds-ring">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
              </div>
            </div>
          </div>
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
                    {{ material.materialName }}
                  </option>
                </select>
              </td>
              <td>
                <template v-if="dishMas.materialId !== 0">
                  {{ (dishMas.unitPrice !== null) ? number_with_commas(Math.ceil(dishMas.unitPrice)) : 0 }}đ /
                  <span class="default-text">{{ dishMas.unit }}</span>
                </template>
              </td>
              <td>
                <div v-if="dishMas.materialId !== null" style="width: 100%; display: flex; align-items: center">
                  <input type="text" style="width: 85%" class="textalign-right mr-1" v-model="dishMas.quantity"
                         v-mask="mask_decimal_limit(5)"
                         @keyup="_handleMaterialUnitPrice(key, dishMas.unitPrice, dishMas.quantity)">
                  <div style="word-break: break-word">({{ dishMas.unit }})</div>
                </div>
              </td>
              <td>
                {{ (dishMas.cost !== null) ? number_with_commas(dishMas.cost) : 0 }}đ
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
            {{ item }}
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
  isLostConnect,
  resizeImage,
} from "../../../static";
import $swal from "sweetalert2";

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
      dishCodeCanGenerate: true,
      imageUploading: false,
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
      this.$store.dispatch('openLoader');
      this.$store.dispatch('getAllCategories')
        .then(({data}) => {
          this.categories = data;
          this.initOptions();
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    initOptions() {
      this.$store.dispatch('openLoader')
      this.$store.dispatch('getAllOptions')
        .then(({data}) => {
          this.options = data;
          this.initMaterials();
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    initMaterials() {
      this.$store.dispatch('openLoader')
      this.$store.dispatch('getAllMaterial')
        .then(({data}) => {
          this.quantifiers = data;
        }).catch(error => {
        if (!isLostConnect(error)) {

        }
      }).finally(() => {
        this.$store.dispatch('closeLoader');
      })
    },
    _handleDishNameChange() {
      this.dishData.dishCode = convert_code(this.dishData.dishName);
    },
    _handleDishCodeInput() {
      this.dishCodeCanGenerate = false;
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
    _handleInputImageChange(ev) {
      if (!this.imageUploading) {
        if (window.File && window.FileReader && window.FileList && window.Blob) {
          let file = document.getElementById('dish_addnew_image').files[0];
          if (file) {
            this.imageUploading = true;
            const reader = new FileReader();
            console.log(file)
            reader.onload = e => {
              resizeImage(ev.target.files[0].type, e.target.result, (result) => {
                this.$store.dispatch('uploadImageToImgur', result)
                  .then(({data}) => {
                    this.dishData.imageUrl = data.data.link;
                    console.log(this.dishData.imageUrl)
                  }).catch(error => {
                  console.log(error.response)
                }).finally(() => {
                  this.imageUploading = false;
                })
              });
            };
            reader.readAsDataURL(file);
          }
        }
      }
    },
    _handleButtonRemoveImage() {
      this.dishData.imageUrl = null;
      document.getElementById('dish_addnew_image').value = '';
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
      if (this.dishCodeCanGenerate) {
        this.dishData.dishCost = this.dishData.cost * 2
      }
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
        if (this.imageUploading) {
          this.$swal({
            position: 'top-end',
            icon: 'warning',
            title: 'Ảnh chưa xử lý xong',
            showConfirmButton: false,
            timer: 5000,
            toast: true,
          });
        } else {

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
          if (check_null(this.dishData.cost)) {
            this.formError.list.push('Giá nguyên vật liệu không được để trống');
            this.formError.isShow = true;
          } else if (this.dishData.cost <= 0) {
            this.formError.list.push('Giá nguyên vật liệu phải lớn hơn 0');
            this.formError.isShow = true;
          }
          if (check_null(this.dishData.dishCost)) {
            this.formError.list.push('Giá thành phẩm không được để trống');
            this.formError.isShow = true;
          } else if (this.dishData.dishCost <= 0) {
            this.formError.list.push('Giá thành phẩm phải lớn hơn 0');
            this.formError.isShow = true;
          }
          if (check_null(this.dishData.defaultPrice)) {
            this.formError.list.push('Giá bán không được để trống');
            this.formError.isShow = true;
          } else if (this.dishData.defaultPrice <= 0) {
            this.formError.list.push('Giá bán phải lớn hơn 0');
            this.formError.isShow = true;
          }
          if (check_null(this.dishData.quantifiers) || this.dishData.quantifiers.length <= 0) {
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
            this.$store.dispatch('openLoader');
            this.$store.dispatch('addNewDish', dishRequest)
              .then(response => {
                this.$swal(`Tạo mới thành công`,
                  'Danh sách thực đơn đã được cập nhật lên hệ thống.',
                  'success').then(result => {
                  this.$router.push({name: 'backend-dish'});
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
}
</script>
