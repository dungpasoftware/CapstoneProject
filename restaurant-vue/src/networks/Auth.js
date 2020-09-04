import Axios from "./index";

export const loginUser = (token, {phone, password}) => {
  return Axios.post(`/login`, {phone, password}
  );
};

export const preLogin = (token) => {
  return Axios.post(`/pre-login`, {}, {
    headers: {
      token
    }
  })
}
