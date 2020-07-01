<template>
  <div class="rs-body">
    <ul class="body-menu">
      <li class="menu-item">
        <router-link class="item__link" to="/backend/cashier">
          <div class="item__icon">
            <i class="fad fa-cash-register"/>
          </div>
          <div class="item__hover">
            <div class="item__hover--header">
              Thu ngân
            </div>
          </div>
        </router-link>
      </li>
      <li class="menu-item">
        <router-link class="item__link" to="/backend/food">
          <div class="item__icon">
            <i class="fas fa-drumstick-bite"/>
          </div>
          <div class="item__hover">
            <div class="item__hover--header">
              Quản lý thực đơn
            </div>
          </div>
        </router-link>
      </li>
      <li class="menu-item">
        <button class="item__link" @click="increment">
          <div class="item__icon">
            <i class="fas fa-drumstick-bite"/>
          </div>
          <div class="item__hover">
            <div class="item__hover--header">
              Quản lý thực đơn
            </div>
          </div>
        </button>
      </li>
      <li class="menu-item">
        <button class="item__link" @click="decrement">
          <div class="item__icon">
            <i class="fas fa-drumstick-bite"/>
          </div>
          <div class="item__hover">
            <div class="item__hover--header">
              Quản lý thực đơn
            </div>
          </div>
        </button>
      </li>
    </ul>
    <router-view/>
  </div>
</template>

<script>
  import BackendFood from "./BackendFood";
  import {mapMutations} from 'vuex'

  export default {
    name: 'Backend',
    components: {
      BackendFood
    },
    methods: {
      increment () {
        this.$store.dispatch('numberIncrement', 4)
      },
      decrement () {
        this.$store.state.number--
      }
    }
  }
</script>
