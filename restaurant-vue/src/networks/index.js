import Axios from "axios";

Axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';



export const optionAxios = {
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, PUT, POST, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token'
  }
}
export default Axios;
