import Axios from "./index";

export const loginUser = (token, {phone, password}) => {
  return Axios.post(`/login`, {phone, password}
  );
};
