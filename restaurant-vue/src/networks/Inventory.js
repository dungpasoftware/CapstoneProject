import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const insertInventory = (token) => {
  let headers = {
    ...optionAxios.headers,
    token
  }
  return Axios.post(`${ROOT_API}/import`,{
    headers: {
      headers
    }
  });
};

