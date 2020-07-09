import Axios,{ optionAxios } from "./index";
import {ROOT_API} from "../static";

export const loginUser = ({phone, password}) => {
    return Axios.get(`${ROOT_API}/login?phone=${phone}&password=${password}`);
};
