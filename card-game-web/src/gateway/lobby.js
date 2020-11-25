import axios from "axios";

export const postJoinQueue = () => axios.post("/games/queue/join");

export const getCurrentMatch = () => axios.get("/games/mine");
