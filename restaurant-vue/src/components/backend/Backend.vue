<template>
  <div class="rs-body">
    <ul class="body-menu">
      <li class="menu-item">
        <router-link exact tag="div" :to="{ name: 'backend' }"
                     active-class="active"
                     exact-active-class=""
                     class="item__icon">
          <i class="fad fa-chart-bar"></i>
        </router-link>
        <div class="item__hover">
          <router-link exact tag="div" :to="{ name: 'backend' }"
                       active-class="active"
                       exact-active-class=""
                       class="item__hover--header">
            Báo cáo tổng quan
          </router-link>
        </div>
      </li>
      <li class="menu-item">
        <router-link exact tag="div" :to="{ name: 'backend-dish' }"
                     active-class="active"
                     exact-active-class=""
                     class="item__icon">
          <i class="fas fa-drumstick-bite"/>
        </router-link>
        <div class="item__hover">
          <router-link exact tag="div" :to="{ name: 'backend-dish' }"
                       active-class="active"
                       exact-active-class=""
                       class="item__hover--header">
            Quản lý thực đơn
          </router-link>
          <ul class="item__hover--list">
            <router-link exact tag="li"
                         active-class="active"
                         exact-active-class=""
                         :to="{ name: 'backend-category' }">
              Quản lý nhóm thực đơn
            </router-link>
            <router-link exact tag="li"
                         active-class="active"
                         exact-active-class=""
                         :to="{ name: 'backend-option' }">
              Quản lý topping
            </router-link>
            <router-link exact tag="li"
                         active-class="active"
                         exact-active-class=""
                         :to="{ name: 'backend-material' }">
              Quản lý nguyên vật liệu
            </router-link>
          </ul>
        </div>
      </li>
      <li class="menu-item">
        <router-link exact tag="div" :to="{ name: 'backend-inventory' }"
                     active-class="active"
                     exact-active-class=""
                     class="item__icon">
          <i class="fad fa-warehouse-alt" />
        </router-link>
        <div class="item__hover">
          <router-link exact tag="div" :to="{ name: 'backend-inventory' }"
                       active-class="active"
                       exact-active-class=""
                       class="item__hover--header">
            Quản lý kho
          </router-link>
          <ul class="item__hover--list">
            <router-link exact tag="li"
                         active-class="active"
                         exact-active-class=""
                         :to="{ name: 'backend-inventory-import' }">
              Nhập kho
            </router-link>
          </ul>
        </div>
      </li>
      <li class="menu-item">
        <router-link exact tag="div" :to="{ name: 'backend-staff' }"
                     active-class="active"
                     exact-active-class=""
                     class="item__icon">
          <i class="fad fa-users"></i>
        </router-link>
        <div class="item__hover">
          <router-link exact tag="div" :to="{ name: 'backend-staff' }"
                       active-class="active"
                       exact-active-class=""
                       class="item__hover--header">
            Quản lý nhân viên
          </router-link>
        </div>
      </li>
    </ul>
    <router-view/>
  </div>
</template>

<script>
  import BackendFood from "./backenddish/BackendDishHome";
  import {mapMutations} from 'vuex'

  export default {
    name: 'Backend',
    components: {
      BackendFood
    },
    methods: {
      increment() {
        // this.$cookies.set('role_name', '02834434783')
        this.$cookies.remove('user_token')
      },
      decrement() {
        this.$store.state.number--
      }
    }
  }
</script>
