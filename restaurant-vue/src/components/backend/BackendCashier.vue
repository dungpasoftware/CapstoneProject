<template>
  <div class="be-cashier animate__animated animate__fadeIn animate__faster">
    <div class="cashier-body">
      <div class="list-table">
        <div class="table-left">
          <ul class="list-phong">
            <li>
              <button class="phong__button active">
                Tất cả
              </button>
            </li>
            <li>
              <button class="phong__button">
                Tầng 1
              </button>
            </li>
            <li>
              <button class="phong__button">
                Tầng 2
              </button>
            </li>
          </ul>
        </div>
        <div class="table-right">
          <div class="right-top">
            <div class="right_title">
              Yêu cầu thanh toán
            </div>
            <div class="right_body">
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
            </div>
          </div>
          <div class="right-bottom">
            <div class="right_title">
              Chưa yêu cầu
            </div>
            <div class="right_body">
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item inuse">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
              <button class="ban-item">
                Bàn 1-1
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="cashier-detail">
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
          <div class="list-item">
            <div class="item-name">
              7 Up
            </div>
            <div class="item-count">
              1
            </div>
            <div class="item-cash">
              20.000đ
            </div>
          </div>
          <div class="list-item">
            <div class="item-name">
              7 Up
            </div>
            <div class="item-count">
              1
            </div>
            <div class="item-cash">
              20.000đ
            </div>
          </div>
          <div class="list-item">
            <div class="item-name">
              7 Up
            </div>
            <div class="item-count">
              1
            </div>
            <div class="item-cash">
              20.000đ
            </div>
          </div>
          <div class="list-item">
            <div class="item-name">
              7 Up
            </div>
            <div class="item-count">
              1
            </div>
            <div class="item-cash">
              20.000đ
            </div>
          </div>
        </div>
        <div class="top-note">
          <div class="note-body">
            <i class="fad fa-comment-alt-lines"/>
            <div class="note-content">
              Lười hiện tại có họ hàng với loài lười mặt đất là loài lười cổ đã bị tuyệt chủng. Chúng không có răng nanh, sống trên mặt đất và có quan hệ họ hàng với loài lười ngày nay. Lười mặt đất có bộ móng sắc nhọn có thể đứng thẳng bằng hai chân sau. Chúng dài gần 6m và có trọng
            </div>
          </div>
        </div>
        <div class="top-detail">
          <div class="detail-item">
            <div class="item-left">
              Tổng tiền:
            </div>
            <div class="item-right">
              129.999đ
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Số lượng:
            </div>
            <div class="item-right">
              5
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Khách đưa:
            </div>
            <div class="item-right">
              <input class="item-input" type="number"/>
              đ
            </div>
          </div>
          <div class="detail-item">
            <div class="item-left">
              Trả lại:
            </div>
            <div class="item-right">
              129.999đ
            </div>
          </div>
        </div>
      </div>
      <div class="detail-option">
        <div class="option-item">
          <button class="item-btn">
            <i class="fal fa-retweet"/><br/>
            Làm mới
          </button>
        </div>
        <div class="option-item">
          <button class="item-btn">
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
              Cập nhật
            </button>
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
  </div>
</template>

<script>
  export default {
    name: 'BackendCashier'
  }
</script>
