<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách Topping
      </div>
      <div class="list-body">
        <div class="list__option">
          <router-link tag="button" :to="{ name: 'backend-option-addnew' }"
            class="btn-default-green">
            Thêm Topping
          </router-link>
        </div>
        <table v-if="(options && options.length > 0)" class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Tên
            </th>
            <th>
              Hình thức
            </th>
            <th>
              Đơn vị
            </th>
            <th>
              Giá NVL
            </th>
            <th>
              Lựa chọn
            </th>
          </tr>
          </thead>
          <tbody v-if="options !== null">
          <tr v-for="(option, key, index) in options"
              :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              <span>{{(option.optionName !== null) ? option.optionName : ''}}</span>
            </td>
            <td>
              <span>
                {{
                  (option.optionType === 'MONEY') ? 'Thêm tiền' :
                  (option.optionType === 'ADD') ? 'Không tính tiền' : ''
                }}
              </span>
            </td>
            <td>
              <span>{{(option.unit !== null) ? option.unit : '- -'}}</span>
            </td>
            <td style="word-break: unset">
              <span>{{ (option.cost !== null) ? `${number_with_commas(Math.ceil(option.cost))}đ` : '- -' }}</span>
            </td>
            <td>
              <div v-if="!option.isEdit" class="table__option table__option-inline">
                <router-link tag="button" :to="{ name: 'backend-option-edit', params: { id: option.optionId } }"
                  class="btn-default-green btn-xs btn-yellow table__option--link">
                  <i class="fas fa-edit"></i>
                </router-link>
                <button @click="_handleButtonDeleteClick(option)"
                  class="btn-default-green btn-xs btn-red table__option--delete">
                  <i class="fas fa-trash-alt"></i>
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div v-else class="text-center">
          Danh sách trống
        </div>
        <div v-if="pagination.total && pagination.total > 1"
             class="list__pagging">
          <button v-for="(item, key) in pagination.total" :key="key"
                  @click="(key + 1 == pagination.page) ? null : _handlePaggingButton(key + 1)"
                  :class="['pagging-item', (key + 1 === pagination.page) ? 'active' : '']">
            {{ key + 1 }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {number_with_commas, isLostConnect} from "../../../static";

  export default {
    data() {
      return {
        options: null,
        optionIndex: 0,
        pagination: {
          total: 0,
          page: 1
        },
      };
    },
    created() {
      this.initOptions();
    },
    methods: {
      number_with_commas,
      initOptions() {
        let page = (this.pagination.page) ? this.pagination.page : 1;
        this.$store.dispatch('openLoader');
        this.$store.dispatch('getPaginationOptions', page)
          .then(({data}) => {
            this.options = data.result;
            this.pagination.total = data.totalPages;
          }).catch(error => {
          if (!isLostConnect(error)) {
          }
        }).finally(() => {
          this.$store.dispatch('closeLoader');
        })
      },
      _handlePaggingButton(index) {
        this.pagination.page = index;
        this.initCategories();
      },
      _handleButtonDeleteClick(option) {
        this.$swal({
          title: `Xoá topping?`,
          html: 'Bạn có chắc chắn muốn xoá.',
          icon: 'warning',
          confirmButtonText: 'Xoá',
          confirmButtonColor: '#F05348',
          showCancelButton: true,
          cancelButtonText: 'Huỷ',
          showCloseButton: true
        }).then((result) => {
          if (result.value) {
            this.$store.dispatch('openLoader');
            this.$store.dispatch('deleteOptionById', option.optionId)
              .then(response => {
                this.$swal({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Cập nhật danh sách thành công',
                  showConfirmButton: false,
                  timer: 5000
                })
                this.initOptions();
              }).catch(error => {
              if (!isLostConnect(error, false)) {
                this.$swal({
                  title: 'Có lỗi xảy ra',
                  html: 'Vui lòng thử lại',
                  icon: 'warning',
                  showCloseButton: true,
                  confirmButtonText: 'Đóng',
                });
              }
            }).finally(() => {
              this.$store.dispatch('closeLoader');
            })
          }
        })

      }
    }
  }
</script>
