import Axios  from "./index";

export const getById = (orderId, token) => {
  return Axios.get(`/order/${orderId}`,{
    headers: {
      token
    }
  });
};

