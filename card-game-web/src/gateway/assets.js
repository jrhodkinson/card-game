import axios from "axios";

export const getCards = () => axios.get("/assets/cards");

export const getStructures = () => axios.get("/assets/structures");
