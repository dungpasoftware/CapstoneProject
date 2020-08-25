import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/options`,{
    headers: {
      token
    }
  })
};

export const getPagination = (token, page) => {
  return Axios.get(`/options/search?page=${page}`,{
    headers: {
      token
    }
  })
};

export const getById = (token, optionId) => {
  return Axios.get(`/options/${optionId}`,{
    headers: {
      token
    }
  })
}

export const addNew = (token, optionData) => {
  let headers = {
    token
  }
  return Axios.post(`/options`, optionData, {
    headers
  })
}

export const editById = (token, {optionData}) => {
  let headers = {
    token
  }
  return Axios.put(`/options/${optionData.optionId}`, optionData, {
    headers
  })
}

export const deleteById = (token, optionId) => {
  let headers = {
    token
  }
  return Axios.delete(`/options/${optionId}`, {
    headers
  })
}


