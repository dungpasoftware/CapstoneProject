import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const getById = (orderId, token) => {
  return Axios.get(`${ROOT_API}/order/${orderId}`,{
    headers: {
      token
    }
  });
};

