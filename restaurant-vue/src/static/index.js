// export const ROOT_API = "http://192.168.1.29:8080"
// export const ROOT_API = "http://192.168.1.4:8080"
// export const ROOT_API = "http://192.168.1.221:8080"
export const ROOT_API = "http://192.168.1.226:8080"

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

export const convert_code = (str) => xoa_dau(str).toUpperCase().replace(/\s+/g, '-').trim()

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
  return !(input !== null && input !== undefined && input !== '');
}
