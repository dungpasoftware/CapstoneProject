import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/options`,{
    headers: {
      token
    }
  });
};

export const editById = (token, {optionData}) => {
  let headers = {
    token
  }
  return Axios.put(`/options/${optionData.optionId}`, optionData, {
    headers
  })
}


