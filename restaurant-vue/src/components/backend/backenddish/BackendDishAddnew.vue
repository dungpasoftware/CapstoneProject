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
          <input required v-model="dishData.dishName" v-on:input="_handleDishNameChange">
        </div>
        <div class="an-item">
          <label>Giá bán <span class="starr">*</span></label>
          <input required class="input-number" v-model="dishData.defaultPrice" @keypress="_handlePhoneChange($event)">
        </div>
        <div class="an-item">
          <label>Giá nhập <span class="starr">*</span></label>
          <input required class="input-number" v-model="dishData.cost" @keypress="_handlePhoneChange($event)">
        </div>
        <div class="an-item">
          <label>Đơn vị</label>
          <input v-model="dishData.dishUnit">
        </div>
        <div class="an-item">
          <label>Số lượng <span class="starr">*</span></label>
          <input required class="input-number" v-model="dishData.remainQuantity" @keypress="_handlePhoneChange($event)">
        </div>
        <div class="an-item">
          <label>Thời gian hoàn thành ước tính</label>
          <input class="input-number" v-model="dishData.timeComplete" @keypress="_handlePhoneChange($event)">
        </div>
        <div class="an-item">
          <label>Thời gian thông báo</label>
          <input class="input-number" v-model="dishData.timeNotification" @keypress="_handlePhoneChange($event)">
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
            <tr v-for="(dishMas, key) in dishData.quantifiers">
              <td>
              </td>
              <td>
                <select v-model="dishMas.materialId"
                        @change="_handleMaterialSelectChange(key, dishMas.materialId)"
                        v-if="quantifiers !== null && quantifiers.length > 0">
                  <option disabled selected value="0">Chọn tên nguyên vật liệu</option>
                  <option v-for="(material, selectKey, value) in quantifiers"
                          :key="selectKey"
                          :value="material.materialId">
                    {{material.materialName}}
                  </option>
                </select>
              </td>
              <td>
                <template v-if="dishMas.materialId !== 0">
                  {{dishMas.unitExportPrice}}đ / {{dishMas.unitExport}}
                </template>
              </td>
              <td>
                <div v-if="dishMas.materialId !== 0" style="width: 100%; display: flex; align-items: center">
                  <input type="text" class="textalign-right mr-1" v-model="dishMas.quantity"
                         @input="_handleMaterialUnitPrice(key, dishMas.unitExportPrice, dishMas.quantity)"
                         @keypress="_handlePhoneChange($event)">
                  ({{dishMas.unitExport}})
                </div>
              </td>
              <td>
                {{dishMas.cost}}đ
              </td>
              <td>
                <textarea v-model="dishMas.description"></textarea>
              </td>
              <td>
                <button @click="_handleMaterialDelete(key)"
                  class="btn-default-green btn-red btn-xs">Xoá</button>
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
          Tạo mới
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import * as staticFunction from '../../../static'

  export default {
    data() {
      return {
        dishData: {
          dishCode: '', //Required
          dishName: '', //Required
          dishUnit: '', //Required
          defaultPrice: 0, //Required
          cost: 0, //Required
          remainQuantity: 0,
          description: '',
          timeComplete: 0,
          timeNotification: 0,
          imageUrl: null,
          typeReturn: false,
          categories: [],
          options: [],
          quantifiers: []
        },
        categories: null,
        options: null,
        quantifiers: null
      };
    },
    created() {
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
          console.log(data);
          this.quantifiers = data;
        }).catch(error => {
        console.log(error)
      })

      console.log(this.$doeee)
    },
    methods: {
      _handleDishNameChange() {
        this.dishData.dishCode = staticFunction.convert_code(this.dishData.dishName);
      },

      _handlePhoneChange(e) {
        e = (e) ? e : window.event;
        var charCode = (e.which) ? e.which : e.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          e.preventDefault();
        } else {
          return true;
        }
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
          quantifierId: 0,
          unitExport: '',
          unitExportPrice: 0,
          quantity: 0,
          cost: 0,
          description: ''
        })
      },

      _handleMaterialSelectChange(key, materialKey) {
        materialKey = materialKey - 1;
        this.dishData.quantifiers[key].quantifierId = this.quantifiers[materialKey].materialId;
        this.dishData.quantifiers[key].unitExport = this.quantifiers[materialKey].unitExport;
        this.dishData.quantifiers[key].unitExportPrice = this.quantifiers[materialKey].unitExportPrice;
      },

      _handleMaterialUnitPrice(key, unitPrice, quantity) {
        this.dishData.quantifiers[key].cost = unitPrice * quantity;
        this.dishData.cost = 0;
        this.dishData.cost = this.dishData.quantifiers.reduce((accumulator, currentValue) => {
          return accumulator += currentValue.cost
        },0)
        this.dishData.defaultPrice = Math.floor(this.dishData.cost / 0.35) - (Math.floor(this.dishData.cost / 0.35) % 5000)
      },

      _handleMaterialDelete(key) {
        this.dishData.quantifiers.splice(key, 1);
      },

      _handleSaveButtonClick() {
        // console.log(this.dishData)
        this.$store.dispatch('addNewDish', this.dishData)
          .then(response => {
            this.$router.push({name: 'backend-dish'});
          }).catch(error => {
          console.error(error)
        });
      }
    }
  }
</script>
