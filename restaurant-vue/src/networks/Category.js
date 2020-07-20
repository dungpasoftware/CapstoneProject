import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const getAll = (token) => {
  return Axios.get(`${ROOT_API}/categories`,{
    headers: {
      token
    }
  });
};

export const editById = (token, {categoryData}) => {
  let headers = {
    ...optionAxios.headers,
    token
  }
  return Axios.put(`${ROOT_API}/categories/${categoryData.categoryId}`, categoryData, {
    headers
  })
}