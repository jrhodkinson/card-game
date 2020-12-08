import axios from "axios";

export default () => {
  axios.defaults.baseURL = process.env.REACT_APP_API_PATH;
};
