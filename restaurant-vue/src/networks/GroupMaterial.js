import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/group-material/all`,{
    headers: {
      token
    }
  });
};

