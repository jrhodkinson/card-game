import axios from "axios";

export const postLogin = (username) =>
  axios.post("/user/login", { name: username });
