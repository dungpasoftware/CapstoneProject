import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/material/all`,{
    headers: {
      token
    }
  });
};
