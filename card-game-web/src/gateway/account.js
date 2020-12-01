import axios from "axios";

export const postLogin = (name, password) =>
  axios.post("/account/login", { name, password });

export const postLogout = () => axios.post("/account/logout");

export const postRegister = (name, email, password) =>
  axios.post("/account/register", { name, email, password });

export const getMe = () => axios.get("/account/me");
