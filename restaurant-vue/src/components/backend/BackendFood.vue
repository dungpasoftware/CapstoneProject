<template>
  <div class="be-food animate__animated animate__fadeIn animate__faster">
    <h2 class="be-title">
      Thực đơn
    </h2>
    <div class="be-select">
      <div class="be-select--left">
        <input type="text" class="select__name" placeholder="Tên món ăn" />
        <select defaultValue="0" name="" class="select__type">
          <option value="0" disabled>Vui lòng chọn</option>
          <option value="1">Đồ uống có ga</option>
          <option value="2">Café</option>
          <option value="3">Abcd</option>
          <option value="4">Món chính</option>
          <option value="5">Món tráng miệng</option>
        </select>
        <button class="select__search btn-default-green">
          Tìm kiếm
        </button>
      </div>
      <div class="be-select--right">
        <button class="select__add-new btn-default-green">
          Thêm món mới
        </button>
      </div>
    </div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-pizza"/>
        Danh sách món
      </div>
      <div class="list-body">
        <div class="list__option">
          <button class="btn-default-green btn-red">
            Xoá danh sách đã chọn
          </button>
        </div>
        <table class="list__table">
          <thead>
          <tr>
            <th>
              <input type="checkbox" name="" id="" />
            </th>
            <td>
              Mã sản phẩm
            </td>
            <th>
              Hình đại diện
            </th>
            <th>
              Tên món
            </th>
            <th>
              Nhóm thực đơn
            </th>
            <th>
              Giá nguyên/ vật liệu
            </th>
            <th>
              Giá bán (*)
            </th>
            <th>
              STT
            </th>
            <th>
              Lựa chọn
            </th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              <input type="checkbox" name="" />
            </td>
            <td>
              7up
            </td>
            <td>

            </td>
            <td>
              7 Up
            </td>
            <td>
              Đố uống có ga
            </td>
            <td>
              8,000
            </td>
            <td>
              20,000
            </td>
            <td>
              <input class="table__stt" type="number" name="" />
            </td>
            <td>
              <div class="table__option">
                <Link class="btn-default-green btn-xs btn-yellow table__option--link" to="">
                  Chỉnh sửa
                </Link>
                <button class="btn-default-green btn-xs btn-red table__option--delete">Xoá</button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name
  }
</script>
