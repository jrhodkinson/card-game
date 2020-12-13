import axios from "axios";

export const getVersion = () => axios.get("/version");
