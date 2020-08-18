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
  return Axios.put(`/order/accept-payment?accept=1`, {},{
    headers,
    data
  });
}

export const cancelAcceptPayment = (token, orderData) => {
  let headers = {
    token
  }
  let data = orderData
  return Axios.put(`/order/accept-payment?accept=0`, {},{
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

export const cancel = (token, orderdata) => {
  let headers = {
    token
  }
  let data = orderdata;
  return Axios.put(`/order/cancel`, {}, {
    headers,
    data
  })
}
