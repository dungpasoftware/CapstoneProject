<template>
  <header class="rs-navbar">
    <router-link tag="a" class="navbar-brand" to="/">
      RESTAURANT
    </router-link>
    <div class="navbar-option dropdown">
      <button type="button" class="navbar-button" data-toggle="dropdown">
        {{ userName }}
        <i class="fas fa-chevron-down"></i>
      </button>
      <div class="dropdown-menu">
        <router-link exact tag="a" class="dropdown-item" to="/login" active-class="active" exact-active-class="">
          Bán hàng
        </router-link>
        <router-link exact tag="a" class="dropdown-item" to="/backend" active-class="active" exact-active-class="">
          Quản lý
        </router-link>
        <a class="dropdown-item">
          Tài khoản
        </a>
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
    computed: {
      userName() {
        let userData = this.$store.getters.setUserdata;
        if (userData === null) {
          return "Đăng nhập"
        } else {
          return userData.phone
        }
      }
    },
    methods: {
      _handleClickLogout() {
        this.$store.dispatch('logout')
      }
    }
  }
</script>
