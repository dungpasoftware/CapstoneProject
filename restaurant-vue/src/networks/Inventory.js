import Axios  from "./index";

export const getAll = (token) => {
  let headers = {
    token
  }
  return Axios.get(`/imports`, {
    headers
  })
}

export const searchImport = (token, {id, dateFrom, dateTo, page}) => {
  let headers = {
    token
  }
  console.log( dateFrom, dateTo )
  return Axios.get(`/imports/search?id=${id}&dateFrom=${dateFrom}&dateTo=${dateTo}&page=${page}`, {
    headers
  })
}

export const insertInventory = (token, {inventoryData}) => {
  let headers = {
    token
  }
  return Axios.post(`/materials`, inventoryData,{
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
