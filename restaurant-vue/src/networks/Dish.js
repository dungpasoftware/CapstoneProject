import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const getAll = (token) => {
  return Axios.get(`${ROOT_API}/dishes`,{
    headers: {
      token
    }
  });
};

export const getById = (token, {id}) => {
  return Axios.get(`${ROOT_API}/dishes/${id}`, {
    headers: {
      token
    }
  })
}

export const insetDish = (token, {dishData}) => {
  return Axios.post(`${ROOT_API}/dishes`, dishData, {
    headers: {
      token
    }
  });
}


export const editDish = (token, {dishData}) => {
  return Axios.put(`${ROOT_API}/dishes/${dishData}`, dishData, {
    headers: {
      token
    }
  });
}
