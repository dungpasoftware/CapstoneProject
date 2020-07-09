<template>
  <div class="rs-body">
    <ul class="body-menu">
      <router-link exact tag="li"
                   class="menu-item"
                   active-class="active"
                   exact-active-class=""
                   :to="{ name: 'backend-cashier' }" >
        <div class="item__icon">
          <i class="fad fa-cash-register"/>
        </div>
        <div class="item__hover">
          <div class="item__hover--header">
            Thu ngân
          </div>
        </div>
      </router-link>
      <router-link exact tag="li"
                   class="menu-item"
                   active-class="active"
                   exact-active-class=""
                   :to="{ name: 'backend-dish' }" >
        <div class="item__icon">
          <i class="fas fa-drumstick-bite"/>
        </div>
        <div class="item__hover">
          <div class="item__hover--header">
            Quản lý thực đơn
          </div>
        </div>
      </router-link>
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
        // this.$cookies.set('user_name', '02834434783')
        this.$cookies.remove('user_token')
      },
      decrement() {
        this.$store.state.number--
      }
    }
  }
</script>
