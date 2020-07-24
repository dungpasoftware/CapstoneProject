import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/table/all`,{
    headers: {
      token
    }
  });
};

