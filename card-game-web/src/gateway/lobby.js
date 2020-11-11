import axios from "axios";

export const queue = () => axios.post("/games/queue");

export const currentMatch = () => axios.get("/games/mine");
