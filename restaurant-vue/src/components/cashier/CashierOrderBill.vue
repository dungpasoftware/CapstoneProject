<template>
  <div class="option-item">
    <button class="item-btn" @click="_handlePrintBillButtonClick()">
      <i class="fal fa-print"></i><br/>
      In hoá đơn
    </button>
    <div class="bill-body hidden" v-if="orderDetail" >
      <div id="cashier_bill_page" style="padding: 10px;">
        <table style="width: 100%; font-size: 16px; text-align: center;">
          <tr>
            <td style="text-align: center; font-size: 28px">
              {{ $store.getters.getStaffCode }}
            </td>
          </tr>
<!--          <tr>-->
<!--            <td style="text-align: center;">-->
<!--              0123456789-->
<!--            </td>-->
<!--          </tr>-->
        </table>
        <table style="width: 100%; margin-top: 10px; font-size: 16px;">
          <tr>
            <th style="font-size: 16px; text-align: center;">
              Phiếu thanh toán
            </th>
          </tr>
          <tr>
            <th v-if="orderDetail.orderCode" style="text-align: center;">
              Phiếu: {{ orderDetail.orderCode }}
            </th>
          </tr>
        </table>
        <table style="width: 100%; margin-top: 10px; font-size: 16px;">
          <tr>
            <td>
              <strong>Ngày:</strong>
            </td>
            <td v-if="orderDetail.orderDate" style="text-align: right">
              {{ convertDate(orderDetail.orderDate) }}
            </td>
          </tr>
          <tr>
            <td>
              <strong>Vào ra:</strong>
            </td>
            <td v-if="orderDetail.orderDate" style="text-align: right">
              {{ convertFromTo(orderDetail.orderDate) }}
            </td>
          </tr>
          <tr>
            <td>
              <strong>Bàn:</strong>
            </td>
            <td v-if="orderDetail.tableName" style="text-align: right">
              {{ orderDetail.tableName }}
            </td>
          </tr>
          <tr>
            <td>
              <strong>Nhân viên:</strong>
            </td>
            <td style="text-align: right">
              {{ $store.getters.getStaffCode }}
            </td>
          </tr>
          <tr>
            <td>
              <strong>Thu ngân:</strong>
            </td>
            <td style="text-align: right">
              {{ $store.getters.getStaffCode }}
            </td>
          </tr>
        </table>
        <table style="width: 100%; margin-top: 5px; font-size: 16px; border-bottom: 1px solid #000">
          <tr style="border-top: 1px solid #000; border-bottom: 1px solid #000; font-size: 14px;">
            <td>
              Món ăn
            </td>
            <td style="text-align: center">
              Số lượng
            </td>
            <td style="text-align: center">
              Đơn giá
            </td>
            <td style="text-align: right">
              Tiền
            </td>
          </tr>
          <tr v-for="(value, key) in orderDetail.orderDish" :key="key">
            <td>
              {{ (value.dish !== null && value.dish.dishName !== null) ? value.dish.dishName : '' }}
            </td>
            <td style="text-align: center">
              {{ (value.quantityOk !== null) ? value.quantityOk : "" }}
            </td>
            <td style="text-align: center">
              {{ (value.sellPrice !== null) ? number_with_commas(value.sellPrice) : "NULL" }}đ
            </td>
            <td style="text-align: right">
              {{ (value.sumPrice !== null) ? number_with_commas(value.sumPrice) : "NULL" }}đ
            </td>
          </tr>
        </table>
        <table style="width: 100%; margin-top: 5px; font-size: 16px;">
          <tr>
            <td>
              <strong>
                Tổng cộng:
              </strong>
            </td>
            <td style="text-align: right">
              <strong>
                {{ (orderDetail.totalAmount) ? number_with_commas(orderDetail.totalAmount) : 0 }}đ
              </strong>
            </td>
          </tr>
          <tr>
            <td>
              Khách đưa:
            </td>
            <td style="text-align: right">
              {{ (customerGive) ? number_with_commas(customerGive) : 0 }}đ
            </td>
          </tr>
          <tr>
            <td>
              <strong>Trả lại: </strong>
            </td>
            <td style="text-align: right">
              <strong>
                {{ (customerCashBack) ? number_with_commas(customerCashBack) : 0 }}đ
              </strong>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>

  import {
    check_null, number_with_commas
  } from "../../static";
  import Printd from 'printd'

  export default {
    name: 'CashierOrderBill',
    data() {
      return {}
    },
    props: ['orderDetail', 'customerGive', 'customerCashBack'],
    created() {
    },
    methods: {
      check_null,
      number_with_commas,
      convertDate(input) {
        let date = new Date(input);
        return `${(date.getDate() < 10) ? `0${date.getDate()}` : date.getDate()}/${(date.getMonth() < 9) ? `0${date.getMonth() + 1}` : date.getMonth() + 1}/${date.getFullYear()}`
      },
      convertFromTo(input) {
        let start = new Date(input);
        let startDate = `${(start.getDate() < 10) ? `0${start.getDate()}` : start.getDate()}/${(start.getMonth() < 9) ? `0${start.getMonth() + 1}` : start.getMonth() + 1}`
        let startTime = `${(start.getHours() < 10) ? `0${start.getHours()}` : start.getHours()}:${(start.getMinutes() < 9) ? `0${start.getMinutes() + 1}` : start.getMinutes() + 1}`
        let now = new Date(Date.now());
        let nowDate = `${(now.getDate() < 10) ? `0${now.getDate()}` : now.getDate()}/${(now.getMonth() < 9) ? `0${now.getMonth() + 1}` : now.getMonth() + 1}`
        let nowTime = `${(now.getHours() < 10) ? `0${now.getHours()}` : now.getHours()}:${(now.getMinutes() < 9) ? `0${now.getMinutes() + 1}` : now.getMinutes() + 1}`
        return `${startDate} ${startTime} ~ ${nowDate} ${nowTime}`
      },
      _handlePrintBillButtonClick() {
        const cssText = `html{width: 800px, font-family: "Arial"}`;
        const d = new Printd()
        d.print( document.getElementById('cashier_bill_page'), [...cssText] )
      }
    }
  }
</script>
