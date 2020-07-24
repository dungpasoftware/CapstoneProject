import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/supplier/all`,{
    headers: {
      token
    }
  });
};

