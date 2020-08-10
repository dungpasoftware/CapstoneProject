import Axios  from "./index";

export const getById = (orderId, token) => {
  return Axios.get(`/order/${orderId}`,{
    headers: {
      token
    }
  });
};

export const acceptPayment = (token, orderData) => {
  let headers = {
    token
  }
  let data = orderData
  return Axios.put(`/order/accept-payment`, {},{
    headers,
    data
  });
}

export const payment = (token, paymentData) => {
  let headers = {
    token
  }
  let data = paymentData;
  return Axios.put(`/order/payment-order`, {}, {
    headers,
    data
  })
}
