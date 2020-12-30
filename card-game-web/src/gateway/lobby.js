import axios from "axios";

export const postJoinQueue = () => axios.post("/games/queue/join");

export const postLeaveQueue = () => axios.post("/games/queue/leave");

export const getQueueStatus = () => axios.get("/games/queue/status");

export const getActiveGamesCount = () => axios.get("/games/count");
