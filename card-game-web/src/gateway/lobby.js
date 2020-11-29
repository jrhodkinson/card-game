import axios from "axios";

export const postJoinQueue = () => axios.post("/games/queue/join");

export const getQueueStatus = () => axios.get("/games/queue/status");

export const getCurrentMatch = () => axios.get("/games/mine");
