import Axios, {defaultAxios}  from "./index";

export const upload = (imageUrl) => {
  let image = imageUrl.replace('data:image/png;base64,', '')
    .replace('data:image/jpeg;base64,', '');

  let headers = {
    "authorization" : "Client-ID bdb6c24ce19d2a8",
    "x-rapidapi-host" : "imgur-apiv3.p.rapidapi.com",
    "x-rapidapi-key" : "78d7deb48amsha776166a942a636p1d86c1jsnb22d701ceff3",
    "content-type" : "application/json",
    "useQueryString" : "true"
  }

  return defaultAxios.post(`https://imgur-apiv3.p.rapidapi.com/3/image`,{image}, {
    headers,
  });
};

