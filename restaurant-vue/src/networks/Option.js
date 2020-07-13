import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const getAll = (token) => {
  return Axios.get(`${ROOT_API}/options`,{
    headers: {
      token
    }
  });
};

export const editById = (token, {optionData}) => {
  let headers = {
    ...optionAxios.headers,
    token
  }
  return Axios.put(`${ROOT_API}/options/${optionData.optionId}`, optionData, {
    headers
  })
}


