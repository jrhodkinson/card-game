import ReconnectingWebSocket from "reconnecting-websocket";

export const connectToMatchStateWebSocket = () => new ReconnectingWebSocket("ws://localhost:7000/match");

export const MESSAGE_TYPES = {
  MATCH_STATE: "matchState"
};