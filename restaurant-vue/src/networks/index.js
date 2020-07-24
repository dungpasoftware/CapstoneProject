import AxiosDefault from "axios";
import {ROOT_API} from "../static";


const Axios = AxiosDefault.create({
  baseURL: ROOT_API,
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, PUT, POST, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token'
  }
})
export default Axios;
