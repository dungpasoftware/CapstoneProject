import Axios from "axios";
import {ROOT_API} from "../static";

export const loginUser = ({username, password}) => {
    return Axios.post(`${ROOT_API}/auth`, {username, password});
}