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
                      :id="'location_' + value.locationTableId" @click="_handleLocationClick(value.locationTableId)">
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
              <div v-for="(value, key, index) in this.$store.getters.getAllTable" :key="index"
                   v-if="value.orderDto !== null && value.orderDto.orderStatusValue !== null && (value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' || value.orderDto.orderStatusValue === 'ACCEPTED_PAYMENT')"
                   @click="(value.orderDto !== null && value.orderDto.orderId !== null ) ? _handleTableClick(value.orderDto.orderId) : ''"
                   :class="['ban-item',value.statusValue]">
                <div v-if="value.staffDto !== null && value.staffDto.staffCode !== null" class="ban-staff">
                  {{ value.staffDto.staffCode }}
                </div>
                {{ value.tableName }}
                <div v-if="value.orderDto !== null && value.orderDto.orderTime !== null" class="ban-time">
                  {{ value.orderDto.orderTime }}
                </div>
                <div class="ban-order-status"
                     v-if="value.orderDto !== null && value.orderDto.orderStatusValue !== null">
                  <img src="../../assets/image/order-ordered.svg" v-if="value.orderDto.orderStatusValue === 'ORDERED' "
                       alt="">
                  <img src="../../assets/image/order-preparation.svg"
                       v-if="value.orderDto.orderStatusValue === 'PREPARATION' " alt="">
                  <img src="../../assets/image/order-just-cooked.svg"
                       v-if="value.orderDto.orderStatusValue === 'JUST_COOKED' " alt="">
                  <img src="../../assets/image/order-completed.svg"
                       v-if="value.orderDto.orderStatusValue === 'COMPLETED' " alt="">
                  <img src="../../assets/image/order-waiting-for-payment.svg"
                       v-if="value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' " alt="">
                  <img src="../../assets/image/order-accept-payment.svg"
                       v-if="value.orderDto.orderStatusValue === 'ACCEPTED_PAYMENT' " alt="">
                </div>
              </div>
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
                <div class="ban-order-status"
                     v-if="value.orderDto !== null && value.orderDto.orderStatusValue !== null">
                  <img src="../../assets/image/order-ordered.svg" v-if="value.orderDto.orderStatusValue === 'ORDERED' ">
                  <img src="../../assets/image/order-preparation.svg"
                       v-if="value.orderDto.orderStatusValue === 'PREPARATION' ">
                  <img src="../../assets/image/order-just-cooked.svg"
                       v-if="value.orderDto.orderStatusValue === 'JUST_COOKED' ">
                  <img src="../../assets/image/order-completed.svg"
                       v-if="value.orderDto.orderStatusValue === 'COMPLETED' ">
                  <img src="../../assets/image/order-waiting-for-payment.svg"
                       v-if="value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' ">
                  <img src="../../assets/image/order-accept-payment.svg"
                       v-if="value.orderDto.orderStatusValue === 'ACCEPTED_PAYMENT' " alt="">
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
                <div :class="['item-name__top', ((value.quantityOk === 0) ? 'item-empty' : '')]">
                  {{(value.dish !== null && value.dish.dishName !== null) ? value.dish.dishName : ""}}
                </div>
                <div class="item-name__return"
                     v-if="value.orderDishCancels !== null && value.orderDishCancels.length > 0">
                  <i class="fas fa-user-times"></i>
                  <div class="item-name__return--list">
                    <div v-for="(cancel, tkey) in value.orderDishCancels" :key="tkey"
                         class="item-name__return--item">
                      <div>
                        <strong>{{ (cancel.quantityCancel !== null) ? `- ${cancel.quantityCancel}` : '' }}</strong>
                        {{(cancel.commentCancel !== null) ? `(${cancel.commentCancel})` : ''}}
                      </div>
                      <div>
                        <span style="white-space: nowrap" v-if="cancel.cancelDate !== null">
                          <i class="fal fa-clock"></i> {{convert_time(cancel.cancelDate)}}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="item-name__topping" v-if="value.orderDishOptions.length > 0">
                  <i class="fad fa-reply"></i>
                  <div class="item-name__topping--list">
                    <div v-for="(topping, tkey, tindex) in value.orderDishOptions" :key="tindex"
                         class="item-name__topping--item">
                      {{ (topping.optionName !== null) ? `. ${topping.optionName}` : '' }}
                      {{ (topping.quantity !== null) ? `- ${topping.quantity}` : '' }}
                      {{ (topping.optionPrice !== null) ? `- ${number_with_commas(topping.optionPrice)}đ` : '' }}
                    </div>
                  </div>
                </div>
                <div class="item-name__comment" v-if="!check_null(value.comment)">
                  <i class="fad fa-comment-alt-dots"></i>
                  {{ value.comment }}
                </div>
              </div>
              <div :class="['item-count', ((value.quantityOk === 0) ? 'item-empty' : '')]">
                {{(value.quantityOk !== null) ? value.quantityOk : ""}}
              </div>
              <div :class="['item-cash', ((value.quantityOk === 0) ? 'item-empty' : '')]">
                {{(value.sellPrice !== null) ? number_with_commas(value.sellPrice) : "NULL"}}đ
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
        <div class="request-payment" v-if="orderDetail.statusId !== null && orderDetail.statusId === 14">
          <div class="request-payment__body animate__animated animate__bounce">
            <div class="request-payment__content">
              Bàn này đang chờ thanh toán
            </div>
            <button @click="_handleAcceptPaymentButton(orderDetail.orderId)" class="request-payment__button">
              Chấp nhận
            </button>
          </div>
        </div>
        <div class="request-payment"
             v-if="orderDetail.statusId !== null && (orderDetail.statusId === 13 || orderDetail.statusId === 15)">
          <div class="request-payment__body">
            <div class="request-payment__content">
              Đã có thể thanh toán
            </div>
          </div>
        </div>
        <div class="top-detail">
          <div class="detail-item">
            <div class="item-left">
              Tổng tiền:
            </div>
            <div class="item-right">
              {{ (orderDetail.totalAmount !== null) ? number_with_commas(orderDetail.totalAmount) : 0 }}đ
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
              <input v-mask="mask_number" v-model="customerGive" class="item-input"
                     @keyup="_handleCustomerGiveMoney(orderDetail.totalAmount)"/>
              đ
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Trả lại:
            </div>
            <div class="item-right">
              {{ (customerCashBack !== null) ? number_with_commas(customerCashBack) : 0 }}đ
            </div>
          </div>
        </div>
      </div>
      <div class="detail-option">
        <div class="option-item">
          <button class="item-btn" @click="_handleThanhToanButtonClick(orderDetail.totalAmount)">
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
  import {ROOT_API, number_with_commas, check_null, mask_number, remove_hyphen} from "../../static";
  import Stomp from "webstomp-client";
  import cookies from 'vue-cookies'

  export default {
    data() {
      return {
        locationButtonActive: 0,
        orderDetail: null,
        customerGive: null,
        customerCashBack: null,
        tableDetailIndex: null,
        mask_number
      };
    },
    beforeCreate() {
      this.$store.dispatch('setAllLocationTable');
      this.$store.dispatch('setAllTable');
    },
    created() {
      this.connect();
    },
    methods: {
      number_with_commas,
      check_null,
      convert_time(index) {
        let time = new Date(index);
        return `${(time.getHours() < 10) ? `0${time.getHours()}` : time.getHours()}:${(time.getMinutes() < 10) ? `0${time.getMinutes()}` : time.getMinutes()}`
      },
      connect() {
        this.$socket = new SockJS(`${ROOT_API}/rms-websocket`);
        this.$stompClient = Stomp.over(this.$socket);
        this.$stompClient.debug = () => {
        }
        this.$stompClient.connect(
          {
            token: cookies.get('user_token')
          },
          frame => {
            this.$stompClient.subscribe("/topic/tables", ({body}) => {
              let tableData = JSON.parse(body);
              console.log(tableData)
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
        if (this.$stompClient) {
          this.$stompClient.disconnect();
        }
      },
      _handleLocationClick(id) {
        this.locationButtonActive = id;
      },
      _handleTableClick(orderId) {
        this.orderDetail = null;
        this.$store.dispatch('getOrderById', {orderId})
          .then(response => {

            console.log(response.data)
            this.orderDetail = response.data;
            this.customerGive = 0;
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
              console.log(response.data)
              this.orderDetail = response.data;
            }).catch(err => {
            alert(err);
          })
        }
      },
      _handleThanhToanButtonClick(totalAmount) {
        let cash = Math.floor(remove_hyphen(this.customerGive));
        if (totalAmount !== cash) {
          this.$swal({
            title: 'Số tiền nhập chưa đúng',
            text: "Bạn có muốn thanh toán hoá đơn này?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Thanh toán',
            cancelButtonText: 'Trở lại'
          }).then(result => {
            if (result.value) {
              this.$toasted.show('hello billo')
            }
          })
        }
      },
      _handleAcceptPaymentButton(orderId) {
        let dataRequest = {
          orderId,
          cashierStaffId: Math.floor(this.$cookies.get('staff_id'))
        }
        console.log(dataRequest)
        this.$store.dispatch('acceptOrderPayment', dataRequest)
          .then(response => {
            alert('success')
          })
      },
      _handleCustomerGiveMoney(totalAmount) {
        if (!check_null(totalAmount)) {
          let cash = Math.floor(remove_hyphen(this.customerGive));
          this.customerCashBack = totalAmount - cash;
        }
      }
    },
    beforeDestroy() {
      this.disconnect();
      this.$store.dispatch('clearAllTable');
      this.$store.dispatch('clearAllLocationTable');
    }
  }
</script>
