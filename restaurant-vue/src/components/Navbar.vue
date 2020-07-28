<template>
  <header class="rs-navbar">
    <router-link tag="a" class="navbar-brand" to="/">
      RESTAURANT
    </router-link>
    <div class="navbar-option dropdown">
      <button type="button" class="navbar-button" data-toggle="dropdown">
        {{ ($store.getters.getStaffCode !== null) ? $store.getters.getStaffCode : 'Đăng nhập' }}
        <i class="fas fa-chevron-down"></i>
      </button>
      <div class="dropdown-menu">
        <template v-if="$store.getters.getRoleName === 'ROLE_CASHIER'">
          <router-link exact tag="a" class="dropdown-item" :to="{ name: 'login' }" active-class="active"
                       exact-active-class="">
            Bán hàng
          </router-link>
        </template>
        <template v-if="$store.getters.getRoleName === 'ROLE_MANAGER'">
          <router-link exact tag="a" class="dropdown-item" :to="{ name: 'backend' }" active-class="active"
                       exact-active-class="">
            Quản Lý
          </router-link>
        </template>
        <button @click="_handleClickLogout" class="dropdown-item">
          Đăng xuất
        </button>
      </div>
    </div>
  </header>
</template>

<script>
  export default {
    name: 'Navbar',
    data() {
      return {
      }
    },
    created() {
    },
    methods: {
      _handleClickLogout() {
        this.$store.dispatch('logout');
        this.$router.push({name: 'login'});
      }
    }
  }
</script>
