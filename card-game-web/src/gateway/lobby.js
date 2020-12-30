import axios from "axios";

export const postJoinQueue = () => axios.post("/matches/queue/join");

export const postLeaveQueue = () => axios.post("/matches/queue/leave");

export const getQueueStatus = () => axios.get("/matches/queue/status");

export const getActiveMatchCount = () => axios.get("/matches/count");
