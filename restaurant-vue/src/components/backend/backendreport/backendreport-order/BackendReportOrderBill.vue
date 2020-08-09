<template>
  <b-modal id="report_order_bill" @hidden="eventCloseModal" size="md" hide-footer
           hide-header centered>
    <div class="modal-head modal-head-white">
      <button @click="_handlePrintBill"
        class="modal-head__btn btn-default-green btn-yellow">
        In hoá đơn
      </button>
      <div class="modal-head__close" @click="_handleCloseModal">
        <i class="fal fa-times"></i>
      </div>
    </div>
    <div class="bill-body">
      <div id="order_bill_page" style="padding: 10px;">
        <table style="width: 100%; font-size: 16px; text-align: center;">
          <tr>
            <td style="text-align: center; font-size: 28px">
              duc123
            </td>
          </tr>
          <tr>
            <td style="text-align: center;">
              0123456789
            </td>
          </tr>
        </table>
        <table style="width: 100%; margin-top: 10px; font-size: 16px;">
          <tr>
            <th style="font-size: 16px; text-align: center;">
              Phiếu thanh toán
            </th>
          </tr>
          <tr>
            <th style="text-align: center;">
              Phiếu: fjkh938480274
            </th>
          </tr>
        </table>
        <table style="width: 100%; margin-top: 10px; font-size: 16px;">
          <tr>
            <td>
              <strong>Ngày:</strong>
            </td>
            <td style="text-align: right">21-27-2020</td>
          </tr>
          <tr>
            <td>
              <strong>Vào ra:</strong>
            </td>
            <td style="text-align: right">21/27 20:00 ~ 21/07 21:00</td>
          </tr>
          <tr>
            <td>
              <strong>Bàn:</strong>
            </td>
            <td style="text-align: right">Bàn 1</td>
          </tr>
          <tr>
            <td>
              <strong>Nhân viên:</strong>
            </td>
            <td style="text-align: right">Nguyen duc(Ca tối)</td>
          </tr>
          <tr>
            <td>
              <strong>Thu ngân:</strong>
            </td>
            <td style="text-align: right">Nguyen Duc</td>
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
          <tr>
            <td>
              Bò húc
            </td>
            <td style="text-align: center">
              1
            </td>
            <td style="text-align: center">
              20,000
            </td>
            <td style="text-align: right">
              20,000
            </td>
          </tr>
          <tr>
            <td>
              7 Up
            </td>
            <td style="text-align: center">
              1
            </td>
            <td style="text-align: center">
              20,000
            </td>
            <td style="text-align: right">
              20,000
            </td>
          </tr>
        </table>
        <table style="width: 100%; margin-top: 5px; font-size: 16px;">
          <tr>
            <td>
              <strong>
                Tổng cộng
              </strong>
            </td>
            <td style="text-align: right">
              <strong>40,000đ</strong>
            </td>
          </tr>
          <tr>
            <td>
              Khách đưa:
            </td>
            <td style="text-align: right">
              40,000đ
            </td>
          </tr>
          <tr>
            <td>
              <strong>Trả lại: </strong>
            </td>
            <td style="text-align: right">
              <strong>40,000đ</strong>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </b-modal>
</template>

<script>

  import {
    check_null, number_with_commas, print
  } from "../../../../static";
  import Printd from 'printd'

  export default {
    name: 'BackendReportOrderBill',
    data() {
      return {}
    },
    props: [],
    created() {
    },
    methods: {
      check_null,
      number_with_commas,
      eventCloseModal() {
      },
      _handleCloseModal() {
        this.$bvModal.hide('material_report_detail');
      },
      _handlePrintBill() {
        const cssText = `html{width: 800px, font-family: "Arial"}`;
        const d = new Printd()
        d.print( document.getElementById('order_bill_page'), [] )
      }
    }
  }
</script>
