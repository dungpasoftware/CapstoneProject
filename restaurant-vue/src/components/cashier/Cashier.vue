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
            <template v-if="locationTable !== null && locationTable.length > 0">
              <li v-for="(value, key) in locationTable" :key="key">
                <button :class="['phong__button', {active: locationButtonActive === value.locationTableId}]"
                        :id="'location_' + value.locationTableId" @click="_handleLocationClick(value.locationTableId)">
                  {{ value.locationName }}
                </button>
              </li>
            </template>
          </ul>
        </div>
        <div class="table-right">
          <div class="right-top">
            <div class="right_title">
              Chờ thanh toán
            </div>
            <div class="right_body" v-if="listTable !== null && listTable.length > 0">
              <div v-for="(value, key) in listTable" :key="key"
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
                       v-if="value.orderDto.orderStatusValue === 'PREPARATION' " alt="PREPARATION">
                  <img src="../../assets/image/order-just-cooked.svg"
                       v-if="value.orderDto.orderStatusValue === 'JUST_COOKED' " alt="JUST_COOKED">
                  <img src="../../assets/image/order-completed.svg"
                       v-if="value.orderDto.orderStatusValue === 'COMPLETED' " alt="COMPLETED">
                  <img src="../../assets/image/order-waiting-for-payment.svg"
                       v-if="value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' " alt="WAITING_FOR_PAYMENT">
                  <img src="../../assets/image/order-accept-payment.svg"
                       v-if="value.orderDto.orderStatusValue === 'ACCEPTED_PAYMENT' " alt="ACCEPTED_PAYMENT">
                </div>
              </div>
            </div>
          </div>
          <div class="right-bottom">
            <div class="right_title ">
              Danh sách bàn
              <button @click="initAllTable" class="right_title__reload">
                <i class="fas fa-redo-alt"></i>
              </button>
            </div>
            <div v-if="listTable !== null && listTable.length > 0" class="right_body">
              <button v-for="(value, key) in listTable" :key="key"
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
                  <img src="../../assets/image/order-preparation.svg" alt="PREPARATION"
                       v-if="value.orderDto.orderStatusValue === 'PREPARATION' ">
                  <img src="../../assets/image/order-just-cooked.svg" alt="JUST_COOKED"
                       v-if="value.orderDto.orderStatusValue === 'JUST_COOKED' ">
                  <img src="../../assets/image/order-completed.svg" alt="COMPLETED"
                       v-if="value.orderDto.orderStatusValue === 'COMPLETED' ">
                  <img src="../../assets/image/order-waiting-for-payment.svg" alt="WAITING_FOR_PAYMENT"
                       v-if="value.orderDto.orderStatusValue === 'WAITING_FOR_PAYMENT' ">
                  <img src="../../assets/image/order-accept-payment.svg" alt="ACCEPTED_PAYMENT"
                       v-if="value.orderDto.orderStatusValue === 'ACCEPTED_PAYMENT' ">
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
      <div class="detail-title">
        {{ (orderDetail.tableName !== null) ? orderDetail.tableName : '' }}
      </div>
      <div class="detail-top">
        <div class="top-list">
          <div class="list-item list-header">
            <div class="item-name">
              Tên món ăn
            </div>
            <div class="item-count">
              SL
            </div>
            <div class="item-unitprice">
              x Đơn giá
            </div>
            <div class="item-cash">
              Thành tiền
            </div>
          </div>
          <template v-if="orderDetail.orderDish.length > 0">
            <div class="list-item" v-for="(value, key) in orderDetail.orderDish" :key="key">
              <div class="item-name">
                <div :class="['item-name__top', ((value.quantityOk === 0) ? 'item-empty' : '')]">
                  {{ (value.dish !== null && value.dish.dishName !== null) ? value.dish.dishName : "" }}
                </div>
                <div class="item-name__return"
                     v-if="value.orderDishCancels !== null && value.orderDishCancels.length > 0">
                  <i class="fas fa-user-times"></i>
                  <div class="item-name__return--list">
                    <div v-for="(cancel, tkey) in value.orderDishCancels" :key="tkey"
                         class="item-name__return--item">
                      <div>
                        <strong>{{ (cancel.quantityCancel !== null) ? `- ${cancel.quantityCancel}` : '' }}</strong>
                        {{ (cancel.commentCancel !== null) ? `(${cancel.commentCancel})` : '' }}
                      </div>
                      <div>
                        <span style="white-space: nowrap" v-if="cancel.cancelDate !== null">
                          <i class="fal fa-clock"></i> {{ convert_time(cancel.cancelDate) }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="item-name__topping" v-if="value.orderDishOptions.length > 0">
                  <i class="fad fa-reply"></i>
                  <div class="item-name__topping--list">
                    <div v-for="(topping, tkey) in value.orderDishOptions" :key="tkey"
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
                {{ (value.quantityOk !== null) ? value.quantityOk : "" }}
              </div>
              <div :class="['item-unitprice', ((value.quantityOk === 0) ? 'item-empty' : '')]">
                x {{ (value.sellPrice !== null) ? number_with_commas(value.sellPrice) : "NULL" }}đ
              </div>
              <div :class="['item-cash', ((value.quantityOk === 0) ? 'item-empty' : '')]">
                {{ (value.sumPrice !== null) ? number_with_commas(value.sumPrice) : "NULL" }}đ
              </div>
            </div>
          </template>
          <div v-else class="text-center mt-1">Chưa có order</div>
        </div>
        <div class="top-note" v-if="orderDetail.comment !== null">
          <div class="note-body">
            <i class="fad fa-comment-alt-lines"/>
            <div class="note-content">
              {{ orderDetail.comment }}
            </div>
          </div>
        </div>
        <div class="request-payment" v-if="orderDetail.statusId !== null && orderDetail.statusId === 14">
          <div class="request-payment__body animate__animated animate__headShake">
            <div class="request-payment__content">
              Bàn này đang chờ thanh toán
            </div>
            <div>
              <button @click="_handleAcceptPaymentButton(orderDetail.orderId)" class="request-payment__button">
                Chấp nhận
              </button>
              <button @click="_handleDisableAcceptPaymentButton(orderDetail.orderId)" class="request-payment__cancel">
                Huỷ
              </button>
            </div>
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
              Tổng số lượng:
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
              <input v-mask="mask_number_limit(13)" v-model="customerGive" class="item-input"
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
          <button class="item-btn" @click="_handleThanhToanButtonClick(orderDetail, orderDetail.totalAmount)">
            <i class="fal fa-cash-register"/><br/>
            Thanh toán
          </button>
        </div>
      </div>
    </div>
    <div class="cashier-detail" v-else>

    </div>
  </div>
</template>

<script>

import SockJS from "sockjs-client";
import {ROOT_API, number_with_commas, check_null, mask_number_limit, remove_hyphen, isLostConnect} from "../../static";
import Stomp from "webstomp-client";
import cookies from 'vue-cookies'

export default {
  data() {
    return {
      locationTable: null,
      listTable: null,
      locationButtonActive: 0,
      orderDetail: null,
      customerGive: null,
      customerCashBack: 0,
      tableDetailIndex: null,
      socketOrder: null,
      socketOrderId: null,
      socketInterval: null,
      mask_number_limit
    };
  },
  beforeCreate() {
  },
  created() {
    this.initLocation();
    this.initAllTable();
    this.connect();
  },
  methods: {
    number_with_commas,
    check_null,
    convert_time(index) {
      let time = new Date(index);
      return `${(time.getHours() < 10) ? `0${time.getHours()}` : time.getHours()}:${(time.getMinutes() < 10) ? `0${time.getMinutes()}` : time.getMinutes()}`
    },
    initLocation() {
      this.$store.dispatch('setAllLocationTable')
        .then(response => {
          this.locationTable = response.data
        }).catch(err => {
        console.log(err)
      })
    },
    initAllTable() {
      this.$store.dispatch('setAllTable')
        .then(res => {
          this.listTable = res.data;
        }).catch(err => {
        console.error(err)
      })
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
          this.subcribeTable();
          this.socketInterval = setInterval(() => {
            this.subcribeTable();
            if (this.socketOrderId !== null) {
              this.socketOrder = this.$stompClient.subscribe(`/topic/orderdetail/${this.socketOrderId}`, ({body}) => {
                let orderDetailData = JSON.parse(body);
                this.orderDetail = orderDetailData;
              });
            }
          }, 10000);
        },
        error => {
          clearInterval(this.socketInterval);
          this.connect();
        }
      );

    },
    disconnect() {
      if (this.$stompClient) {
        this.$stompClient.disconnect();
        clearInterval(this.socketInterval);
      }
    },
    subcribeTable() {
      this.$stompClient.subscribe("/topic/tables", ({body}) => {
        let tableData = JSON.parse(body);
        this.listTable = tableData;
      });
    },
    subcribeOrder(orderId) {
      if (this.socketOrderId !== null) {
        this.socketOrder.unsubscribe();
      }
      this.socketOrderId = orderId;
      this.socketOrder = this.$stompClient.subscribe(`/topic/orderdetail/${this.socketOrderId}`, ({body}) => {
        let orderDetailData = JSON.parse(body);
        this.orderDetail = orderDetailData;
      });
    },
    _handleLocationClick(id) {
      this.locationButtonActive = id;
    },
    _handleTableClick(orderId) {
      this.orderDetail = null;
      this.$store.dispatch('getOrderById', {orderId})
        .then(response => {
          this.orderDetail = response.data;
          this.customerGive = null;
          this.customerCashBack = 0;
          this.tableDetailIndex = orderId;
          this.subcribeOrder(orderId);
          console.log(this.orderDetail)
        }).catch(err => {
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
    _handleThanhToanButtonClick(orderDetail, totalAmount) {
      if (orderDetail.statusId === 13 || orderDetail.statusId === 15) {
        let cash = !check_null(this.customerGive) ? parseFloat(remove_hyphen(this.customerGive)) : 0;
        if (totalAmount !== cash) {
          let message = (totalAmount > cash) ? `Khách hàng đưa thiếu ${number_with_commas(totalAmount - cash)}đ` :
            `Trả lại tiền thừa ${number_with_commas(cash - totalAmount)}đ cho khách hàng`;

          this.$swal({
            title: 'Xác nhận thanh toán',
            text: message,
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Thanh toán ngay',
            cancelButtonText: 'Trở lại'
          }).then(result => {
            if (result.value) {
              let dataRequest = {
                orderId: orderDetail.orderId,
                cashierStaffId: Math.ceil(this.$cookies.get('staff_id')),
                customerPayment: cash,
              }
              this.$store.dispatch('paymentOrder', dataRequest)
                .then(res => {
                  this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Thanh toán thành công',
                    showConfirmButton: false,
                    timer: 5000,
                    toast: true,
                  })
                  this.orderDetail = null;
                  this.socketOrder.unsubscribe();
                }).catch(err => {
                console.error(err)
              })
            }
          })
        } else {
          let dataRequest = {
            orderId: orderDetail.orderId,
            cashierStaffId: Math.ceil(this.$cookies.get('staff_id')),
            customerPayment: cash,
          }
          this.$store.dispatch('paymentOrder', dataRequest)
            .then(res => {
              this.$swal({
                position: 'top-end',
                icon: 'success',
                title: 'Thanh toán thành công',
                showConfirmButton: false,
                timer: 5000,
                toast: true,
              })
              this.orderDetail = null;
              this.socketOrder.unsubscribe();
            }).catch(err => {
            console.error(err)
          })
        }
      } else {
        this.$swal({
          position: 'top-end',
          icon: 'warning',
          title: 'Bàn này chưa thể thanh toán',
          showConfirmButton: false,
          timer: 5000,
          toast: true,
        })
      }
    },
    _handleAcceptPaymentButton(orderId) {
      let dataRequest = {
        orderId,
        cashierStaffId: Math.ceil(this.$cookies.get('staff_id'))
      }
      console.log(dataRequest)
      this.$store.dispatch('acceptOrderPayment', dataRequest)
        .then(response => {
          this.$swal({
            position: 'top-end',
            icon: 'success',
            title: 'Đã chấp nhận thanh toán',
            showConfirmButton: false,
            timer: 5000,
            toast: true,
          })
        })
    },
    _handleDisableAcceptPaymentButton(orderId) {
      let dataRequest = {
        orderId,
        cashierStaffId: Math.ceil(this.$cookies.get('staff_id'))
      }
      console.log(dataRequest)
      this.$store.dispatch('cancelOrderPayment', dataRequest)
        .then(response => {
          this.$swal({
            position: 'top-end',
            icon: 'error',
            title: 'Đã huỷ yêu cầu thanh toán',
            showConfirmButton: false,
            timer: 5000,
            toast: true,
          })
        }).catch(error => {
        if (!isLostConnect(error, false)) {
          console.log(error.response)
        }
      })
    },
    _handleCustomerGiveMoney(totalAmount) {
      if (!check_null(totalAmount)) {
        let cash = parseFloat(remove_hyphen(this.customerGive));
        this.customerCashBack = ((cash - totalAmount) > 0) ? cash - totalAmount : 0;
      }
    }
  },
  beforeDestroy() {
    this.disconnect();
  }
}
</script>
