import axios from "axios";

export const postLogin = (username) =>
  axios.post("/account/login", { name: username });

export const getMe = () => axios.get("/account/me");
