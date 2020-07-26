<template>
  <div>
    <div class="food-list">
      <div class="list__title">
        <i class="fad fa-clipboard-list"></i>
        Danh sách nguyên vật liệu
      </div>
      <div class="list-body">
        <table class="list__table">
          <thead>
          <tr>
            <th>
              STT
            </th>
            <th>
              Mã NVL
            </th>
            <th>
              Tên NVL
            </th>
            <th>
              Đơn vị tính
            </th>
            <th>
              Đơn giá
            </th>
            <th>
              Giá bán
            </th>
            <th>
              Tồn kho
            </th>
            <th>
              Nhóm NVL
            </th>
          </tr>
          </thead>
          <tbody v-if="materials !== null">
          <tr v-for="(material, key, index) in materials"
              :key="key">
            <td>
              {{ key + 1 }}
            </td>
            <td>
              {{ material.materialCode }}
            </td>
            <td>
              {{ material.materialName }}
            </td>
            <td>
              {{ material.unit }}
            </td>
            <td>
              {{ material.unitPrice }}
            </td>
            <td>
              {{ material.totalPrice }}
            </td>
            <td>
              {{ material.remain }}
            </td>
            <td>
              {{ material.groupMaterial.groupName }}
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  import * as staticFunction from '../../../static'

  export default {
    data() {
      return {
        materials: null,
        optionIndex: 0,
      };
    },
    created() {
      this.initOptions();
    },
    methods: {
      initOptions() {
        this.$store.dispatch('getAllMaterial')
          .then(({data}) => {
            console.log(data)
            this.materials = data;
          }).catch(error => {
          console.log(error)
        })
      },
      numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      },
      _handleButtonSaveClick(option) {
        this.$store.dispatch('editOptionById', option)
          .then(response => {
            this.initOptions();
            alert('Success')
          }).catch(err => {
          console.error(err)
        })
      }
    }
  }
</script>
