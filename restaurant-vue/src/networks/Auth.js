import Axios from "axios";
import {ROOT_API} from "../static";

export const loginUser = ({phone, password}) => {
    return Axios.post(`${ROOT_API}/auth`, {phone, password});
}
