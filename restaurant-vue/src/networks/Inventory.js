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
  return Axios.post(`/imports/inventory`, inventoryData,{
    headers
  });
};

export const insertExistInventory = (token, inventoryData) => {
  let headers = {
    token
  }
  console.log(inventoryData)
  return Axios.post(`/imports/existInventory`, inventoryData, {
    headers
  })
}
