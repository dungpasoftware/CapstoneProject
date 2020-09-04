import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/staffs`,{
    headers: {
      token
    }
  });
};

export const addNew = (token, staffData) => {
  return Axios.post(`/staffs`, staffData,{
    headers: {
      token
    }
  });
}
