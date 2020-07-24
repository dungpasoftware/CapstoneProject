import Axios  from "./index";

export const insertInventory = (token, {inventoryData}) => {
  let headers = {
    token
  }
  return Axios.post(`/import/inventory`, inventoryData,{
    headers
  });
};
