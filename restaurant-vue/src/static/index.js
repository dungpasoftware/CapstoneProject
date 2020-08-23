import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import $swal from 'sweetalert2'
import cookies from 'vue-cookies'


// export const ROOT_API = "http://localhost:8080"
// export const ROOT_API = "http://192.168.1.29:8080"
// export const ROOT_API = "http://192.168.1.4:8080"
// export const ROOT_API = "http://192.168.1.221:8080"
// export const ROOT_API = "http://192.168.1.226:8080"
// export const ROOT_API = "http://123.16.212.77:8080"
export const ROOT_API = "http://103.92.28.192:8080"

export const xoa_dau = (str) => {
  str = str.replace(/[àáạảãâầấậẩẫăằắặẳẵ]/g, "a");
  str = str.replace(/[èéẹẻẽêềếệểễ]/g, "e");
  str = str.replace(/[ìíịỉĩ]/g, "i");
  str = str.replace(/[òóọỏõôồốộổỗơờớợởỡ]/g, "o");
  str = str.replace(/[ùúụủũưừứựửữ]/g, "u");
  str = str.replace(/[ỳýỵỷỹ]/g, "y");
  str = str.replace(/đ/g, "d");
  str = str.replace(/[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]/g, "A");
  str = str.replace(/[ÈÉẸẺẼÊỀẾỆỂỄ]/g, "E");
  str = str.replace(/[ÌÍỊỈĨ]/g, "I");
  str = str.replace(/[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]/g, "O");
  str = str.replace(/[ÙÚỤỦŨƯỪỨỰỬỮ]/g, "U");
  str = str.replace(/[ỲÝỴỶỸ]/g, "Y");
  str = str.replace(/Đ/g, "D");
  return str;
}

export const number_with_commas = (x) => {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

export const convert_code = (str) => xoa_dau(str).toUpperCase().trim().replace(/\s+/g, '-')

export const check_number = (e) => {
  e = (e) ? e : window.event;
  var charCode = (e.which) ? e.which : e.keyCode;
  if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
    e.preventDefault();
  } else {
    return true;
  }
}

export const check_float = (input) => {
  let reg = new RegExp('\\d{0,2}(\\.\\d{0,2}){0,1}$');
  return !reg.test(input);
}

export const check_null = (input) => {
  return !(input !== null && input !== undefined && input !== '' );
}

export const remove_hyphen = (input) => {
  return input.replace(/,/g,'');
}

export const mask_number = createNumberMask({
  prefix: '',
  allowDecimal: false,
  includeThousandsSeparator: true,
  allowNegative: false,
});



export const mask_number_limit = (limit) => createNumberMask({
  prefix: '',
  allowDecimal: false,
  includeThousandsSeparator: true,
  allowNegative: false,
  integerLimit: limit,
});


export const mask_decimal = createNumberMask({
  prefix: '',
  allowDecimal: true,
  includeThousandsSeparator: true,
  allowNegative: false,
  decimalLimit: 3,
});


export const mask_decimal_limit = (limit) => createNumberMask({
  prefix: '',
  allowDecimal: true,
  includeThousandsSeparator: true,
  allowNegative: false,
  integerLimit: limit,
  decimalLimit: 3,
});

export const isLostConnect = (error, isReload = true, onLogin = false) => {
  console.log(error);
  if (!error.response || error.message === 'timeout' || error.request === 'Network Error') {
    return $swal.fire({
      title: 'Hệ thống không phản hồi',
      text: 'Vui lòng kiểm tra lại đường truyền',
      icon: 'question',
      allowOutsideClick: false,
      confirmButtonText: isReload ? 'Thử lại' : 'Đóng',
    }).then(result => {
      if (result.value) {
        if (isReload) {
          location.reload();
        }
      }
      return true;
    });
  } else if (error.response && error.response.data.status === 'UNAUTHORIZED') {
    if (!onLogin) {
      cookies.remove('user_token');
      return $swal.fire({
        title: 'Yêu cầu đăng nhập',
        text: 'Vui lòng đăng nhập lại',
        icon: 'warning',
        allowOutsideClick: false,
        confirmButtonText: 'Đăng nhập lại',
      }).then(result => {
        if (result.value) {
          if (isReload) {
            location.reload();
          }
        }
        return true;
      });
    }
  } else {
    return false;
  }
}

export const insertCommasDecimal = (index) => {
  let str = index.toString();
  if (str.includes('.')) {
    let strSub = str.substring(str.indexOf('.'), str.length - 1)
    return (strSub.length > 3) ? number_with_commas(index.toFixed(3)) : number_with_commas(index);
  } else {
    return number_with_commas(index);
  }
}

export const resizeImage = async (type, imageSrc, callBack) => {
  let img = await document.createElement("img");
  img.src = imageSrc;
  let canvas = await document.createElement("canvas");

  let MAX_WIDTH = 400;
  let MAX_HEIGHT = 400;
  let width = img.width;
  let height = img.height;

  if (width > height) {
    if (width > MAX_WIDTH) {
      height *= MAX_WIDTH / width;
      width = MAX_WIDTH;
    }
  } else {
    if (height > MAX_HEIGHT) {
      width *= MAX_HEIGHT / height;
      height = MAX_HEIGHT;
    }
  }
  canvas.width = width;
  canvas.height = height;
  let ctx = await canvas.getContext("2d");
  await ctx.drawImage(img, 0, 0, width, height);

  return callBack(canvas.toDataURL(type));
}

export const check_min_date = (dateInput) => {
  let date = new Date(dateInput);
  if (date.getFullYear() < 2000) {
    console.log('sai vai lon')
    $swal.fire({
      position: 'top-end',
      icon: 'warning',
      title: 'Không được nhập ngày thấp hơn 2000-01-01',
      showConfirmButton: false,
      timer: 5000,
      toast: true,
    })
    return false;
  } else {
    return true;
  }
}
