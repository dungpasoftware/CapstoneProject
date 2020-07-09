import Axios from "axios";

Axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';



export const optionAxios = {
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
    'Access-Control-Allow-Origin': '*'
  }
}
export default Axios;
