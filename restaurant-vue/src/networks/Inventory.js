import Axios  from "./index";

export const getAll = (token) => {
  let headers = {
    token
  }
  return Axios.get(`/imports`, {
    headers
  })
}

export const insertInventory = (token, {inventoryData}) => {
  let headers = {
    token
  }
  return Axios.post(`/import/inventory`, inventoryData,{
    headers
  });
};
