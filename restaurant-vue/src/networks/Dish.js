import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/dishes`,{
    headers: {
      token
    }
  });
};

export const getById = (token, {id}) => {
  return Axios.get(`/dishes/${id}`, {
    headers: {
      token
    }
  })
}

export const insetDish = (token, {dishData}) => {
  return Axios.post(`/dishes`, dishData, {
    headers: {
      token
    }
  });
}


export const editDish = (token, {dishData}) => {
  let headers = {
    token
  }
  return Axios.put(`/dishes/${dishData.dishId}`, dishData, {
    headers
  });
}
