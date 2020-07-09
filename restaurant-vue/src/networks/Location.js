import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const getAll = (token) => {
  return Axios.get(`${ROOT_API}/location-table/all`,{
    headers: {
      token
    }
  });
};

