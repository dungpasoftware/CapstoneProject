<template>
  <div class="be-cashier animate__animated animate__fadeIn animate__faster">
    <div class="cashier-body">
      <div class="list-table">
        <div class="table-left">
          <ul class="list-phong">
            <li>
              <button :class="['phong__button', {active: locationButtonActive === 0}]"
                      id="location_0" @click="_handleLocationClick(0)">
                Tất cả
              </button>
            </li>
            <li v-for="(value, key, index) in this.$store.state.table_locations" :key="index">
              <button :class="['phong__button', {active: locationButtonActive === value.locationTableId}]"
                      :id="'location_' + value.locationTableId" @click="_handleLocationClick(value.locationTableId)" >
                {{value.locationName}}
              </button>
            </li>
          </ul>
        </div>
        <div class="table-right">
          <div class="right-top">
            <div class="right_title">
              Yêu cầu thanh toán
            </div>
            <div class="right_body" v-if="this.$store.getters.getAllTable != null">
              <button v-for="(value, key, index) in this.$store.getters.getAllTable" :key="index"
                      v-if="value.orderDto !== null && value.orderDto.orderStatusValue !== null && value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT'"
                      @click="(value.orderDto !== null && value.orderDto.orderId !== null ) ? _handleTableClick(value.orderDto.orderId) : ''"
                      :class="['ban-item',value.statusValue]">
                <div v-if="value.staffDto !== null && value.staffDto.staffCode !== null" class="ban-staff">
                  {{ value.staffDto.staffCode }}
                </div>
                {{ value.tableName }}
                <div v-if="value.orderDto !== null && value.orderDto.orderTime !== null" class="ban-time">
                  {{ value.orderDto.orderTime }}
                </div>
                <div class="ban-order-status" v-if="value.orderDto !== null && value.orderDto.orderStatusValue !== null">
                  <img src="../../assets/image/order-ordered.svg" v-if="value.orderDto.orderStatusValue === 'ORDERED' ">
                  <img src="../../assets/image/order-preparation.svg" v-if="value.orderDto.orderStatusValue === 'PREPARATION' ">
                  <img src="../../assets/image/order-just-cooked.svg" v-if="value.orderDto.orderStatusValue === 'JUST_COOKED' ">
                  <img src="../../assets/image/order-completed.svg" v-if="value.orderDto.orderStatusValue === 'COMPLETED' ">
                  <img src="../../assets/image/order-waiting-for-payment.svg" v-if="value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' ">
                </div>
              </button>
            </div>
          </div>
          <div class="right-bottom">
            <div class="right_title">
              Chưa yêu cầu
            </div>
            <div v-if="this.$store.getters.getAllTable != null" class="right_body">
              <button v-for="(value, key, index) in this.$store.getters.getAllTable" :key="index"
                      v-if="locationButtonActive === 0 || value.locationId === locationButtonActive"
                      @click="(value.orderDto !== null && value.orderDto.orderId !== null ) ? _handleTableClick(value.orderDto.orderId) : ''"
                      :class="['ban-item',value.statusValue]">
                <div v-if="value.staffDto !== null && value.staffDto.staffCode !== null" class="ban-staff">
                  {{ value.staffDto.staffCode }}
                </div>
                {{ value.tableName }}
                <div v-if="value.orderDto !== null && value.orderDto.orderTime !== null" class="ban-time">
                  {{ value.orderDto.orderTime }}
                </div>
                <div class="ban-order-status" v-if="value.orderDto !== null && value.orderDto.orderStatusValue !== null">
                  <img src="../../assets/image/order-ordered.svg" v-if="value.orderDto.orderStatusValue === 'ORDERED' ">
                  <img src="../../assets/image/order-preparation.svg" v-if="value.orderDto.orderStatusValue === 'PREPARATION' ">
                  <img src="../../assets/image/order-just-cooked.svg" v-if="value.orderDto.orderStatusValue === 'JUST_COOKED' ">
                  <img src="../../assets/image/order-completed.svg" v-if="value.orderDto.orderStatusValue === 'COMPLETED' ">
                  <img src="../../assets/image/order-waiting-for-payment.svg" v-if="value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' ">
                </div>
              </button>
            </div>
            <div v-else class="right_body">
              Trống
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="cashier-detail" v-if="orderDetail !== null">
      <div class="detail-top">
        <div class="top-list">
          <div class="list-item list-header">
            <div class="item-name">
              Món
            </div>
            <div class="item-count">
              Số lượng
            </div>
            <div class="item-cash">
              Tiền
            </div>
          </div>
          <template v-if="orderDetail.orderDish.length > 0">
            <div class="list-item" v-for="(value, key, index) in orderDetail.orderDish" :key="index">
              <div class="item-name">
                <div class="item-name__top">
                  {{(value.dish !== null && value.dish.dishName !== null) ? value.dish.dishName : ""}}
                </div>
                <div class="item-name__topping" v-if="value.orderDishOptions.length > 0">
                  <i class="fad fa-reply"></i>
                  <div class="item-name__topping--list">
                    <div v-for="(topping, tkey, tindex) in value.orderDishOptions" :key="tindex"
                      class="item-name__topping--item">
                      {{ (topping.optionName !== null) ? topping.optionName : '' }}
                      {{ (topping.quantity !== null) ? `- ${topping.quantity}` : '' }}
                      {{ (topping.sumPrice !== null) ? `- ${topping.sumPrice}` : '' }}
                    </div>
                  </div>
                </div>
              </div>
              <div class="item-count">
                {{(value.quantity !== null) ? value.quantity : ""}}
              </div>
              <div class="item-cash">
                {{(value.sellPrice !== null) ? numberWithCommas(value.sellPrice) : "NULL"}}đ
              </div>
            </div>
          </template>
        </div>
        <div class="top-note" v-if="orderDetail.comment !== null">
          <div class="note-body">
            <i class="fad fa-comment-alt-lines"/>
            <div class="note-content">
              {{orderDetail.comment}}
            </div>
          </div>
        </div>
        <div class="top-detail">
          <div class="detail-item">
            <div class="item-left">
              Tổng tiền:
            </div>
            <div class="item-right">
              {{ (orderDetail.totalAmount !== null) ? numberWithCommas(orderDetail.totalAmount) : 0 }}đ
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Số lượng:
            </div>
            <div class="item-right">
              {{ (orderDetail.totalItem !== null) ? orderDetail.totalItem : 0 }}
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Khách đưa:
            </div>
            <div class="item-right">
              <input v-model="customerCashBack" class="item-input" @keypress="_handleNumberChange($event)"/>
              đ
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Trả lại:
            </div>
            <div class="item-right">
              {{ (orderDetail.totalAmount !== null && customerCashBack !== null) ?
                  numberWithCommas(customerCashBack - orderDetail.totalAmount) : "" }}đ
            </div>
          </div>
        </div>
      </div>
      <div class="detail-option">
        <div class="option-item">
          <button class="item-btn" @click="_handleThanhToanButtonClick">
            <i class="fal fa-cash-register"/><br/>
            Thanh toán
          </button>
        </div>
        <div class="option-item dropdown">
          <button type="button" class="item-btn" data-toggle="dropdown">
            <i class="far fa-ellipsis-h"/><br/>
            Menu
          </button>
          <div class="dropdown-menu dropdown-menu-right">
            <button class="dropdown-item">
              Tạo mới
            </button>
            <button class="dropdown-item">
              Hủy hóa đơn
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="cashier-detail" v-else>

    </div>
  </div>
</template>

<script>

  import SockJS from "sockjs-client";
  import {ROOT_API, USER_TOKEN} from "../../static";
  import Stomp from "webstomp-client";
  import cookies from 'vue-cookies'

  export default {
    data() {
      return {
        locationButtonActive: 0,
        orderDetail: null,
        customerCashBack: 0,
        tableDetailIndex: null
      };
    },
    beforeCreate() {
        this.$store.dispatch('setAllLocationTable');
        this.$store.dispatch('setAllTable');
    },
    created() {
      this.connect();
      // console.log(USER_TOKEN);
    },
    methods: {
      connect() {
        this.$socket = new SockJS(`${ROOT_API}/rms-websocket`);
        this.$stompClient = Stomp.over(this.$socket);
        this.$stompClient.debug = () => {}
        this.$stompClient.connect(
          {
            token: cookies.get('user_token')
          },
          frame => {
            console.log('connected');
            this.$stompClient.subscribe("/topic/tables", ({body}) => {
              let tableData = JSON.parse(body);
              this.$store.dispatch('updateTable', {tableData});
            });
          },
          error => {
            console.error(error);
          }
        );
      },
      disconnect() {
        console.log('disconnected');
        if (this.stompClient) {
          this.stompClient.disconnect();
        }
      },
      _handleLocationClick(id) {
        this.locationButtonActive = id;
      },
      _handleTableClick(orderId) {
        this.$store.dispatch('getOrderById', {orderId})
          .then(response => {
            this.orderDetail = response.data;
            this.customerCashBack = 0;
            this.tableDetailIndex = orderId;
          }).catch(err => {
            alert(err);
            this.tableDetailIndex = null;
        })
      },
      _handleRefreshButtonClick() {
        let orderId = this.tableDetailIndex;
        if (orderId !== null) {
          this.$store.dispatch('getOrderById', {orderId})
            .then(response => {
              this.orderDetail = response.data;
            }).catch(err => {
            alert(err);
          })
        }
      },
      _handleNumberChange(e) {
        e = (e) ? e : window.event;
        var charCode = (e.which) ? e.which : e.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          e.preventDefault();
        } else {
          return true;
        }
      },
      _handleThanhToanButtonClick() {
        this.$swal(
          'The Internet?',
          'That thing is still around?',
          'question'
        );
      },
      numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },
    beforeDestroy() {
      this.disconnect();
      this.$store.dispatch('clearAllTable');
      this.$store.dispatch('clearAllLocationTable');
    }
  }
</script>
