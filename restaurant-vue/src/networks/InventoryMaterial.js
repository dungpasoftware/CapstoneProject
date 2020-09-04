import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/inventory-material/all`,{
    headers: {
      token
    }
  });
};

export const addNew = (token, inventoryData) => {
  return Axios.post(`/inventory-material`, inventoryData, {
    headers: {
      token
    }
  })
};
