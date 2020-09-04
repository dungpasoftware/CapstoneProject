import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/location-table/all`,{
    headers: {
      token
    }
  });
};

