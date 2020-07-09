<template>
  <div class="dish-addnew animate__animated animate__fadeIn animate__faster">
    <div class="an__title">
      <i class="fad fa-pizza"/>
      Chỉnh sửa món
    </div>
    <div class="an-body">
      <div class="an-form">
        <div class="an-item">
          <label>Mã thực đơn</label>
          <input disabled v-model="dishData.dishCode">
        </div>
        <div class="an-item">
          <label>Tên thực đơn <span class="starr">*</span></label>
          <input required v-model="dishData.dishName" v-on:input="_handleDishNameChange" >
        </div>
        <div class="an-item">
          <label>Giá bán <span class="starr">*</span></label>
          <input required class="input-number" v-model="dishData.cost" @keypress="_handlePhoneChange($event)" >
        </div>
        <div class="an-item">
          <label>Giá nhập <span class="starr">*</span></label>
          <input required class="input-number" v-model="dishData.defaultPrice" @keypress="_handlePhoneChange($event)">
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
          <label>Mô tả cho món ăn</label>
          <textarea v-model="dishData.description" rows="3">
          </textarea>
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
  import * as staticFunction from '../../../static'

  export default {
    data() {
      return {
        dishID: this.$route.params.id,
        dishData: {
          dishCode: '',
          dishName: '',
          dishUnit: '',
          defaultPrice: 0,
          cost: 0,
          remainQuantity: 0,
          description: '',
          timeComplete: 0,
          timeNotification: 0,
          imageUrl: null,
          status: {
            statusId: 7,
            statusValue: 'AVAILABLE'
          },
          categories: [],
          options: []
        },
        categories: null,
        opitons: null,
      };
    },
    created() {

      this.$store.dispatch('getAllCategories')
        .then(({data}) => {
          this.categories = data;
        }).catch(error => {
        console.log(error)
      })

      this.$store.dispatch('getDishById', this.dishID)
        .then(response => {
          this.dishData = response.data;
        }).catch(err => {
          console.error(err);
      })
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
      _handleSaveButtonClick() {
        this.$store.dispatch('editDishById', this.dishData)
          .then(response => {
            this.$router.push({name: 'backend-dish'});
          }).catch(error => {
          console.error(error)
        });
      }
    }
  }
</script>
