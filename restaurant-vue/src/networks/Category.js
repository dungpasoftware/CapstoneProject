import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/categories`,{
    headers: {
      token
    }
  });
};

export const addNew = (token, {categoryData}) => {
  let headers = {
    token
  }
  return Axios.post(`/categories`, categoryData, {
    headers
  })
}

export const editById = (token, {categoryData}) => {
  let headers = {
    token
  }
  return Axios.put(`/categories/${categoryData.categoryId}`, categoryData, {
    headers
  })
}

export const deleteById = (token, categoryId) => {
  let headers = {
    token
  }
  return Axios.delete(`/categories/${categoryId}`, {
    headers
  })
}
