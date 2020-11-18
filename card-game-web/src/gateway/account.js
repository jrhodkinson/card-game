import axios from "axios";

export const login = (username) =>
  axios.post("/user/login", { name: username });
