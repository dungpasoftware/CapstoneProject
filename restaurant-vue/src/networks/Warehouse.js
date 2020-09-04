import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/warehouse/all`,{
    headers: {
      token
    }
  });
};

