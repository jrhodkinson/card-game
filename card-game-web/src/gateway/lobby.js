import axios from "axios";

export const postQueue = () => axios.post("/games/queue");

export const getCurrentMatch = () => axios.get("/games/mine");
