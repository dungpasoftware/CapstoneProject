import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";




export const getAll = (token) => {
  return Axios.get(`${ROOT_API}/table/all`,{
    headers: {
      token
    }
  });
};

