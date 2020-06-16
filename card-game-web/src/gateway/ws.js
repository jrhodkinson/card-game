import ReconnectingWebSocket from "reconnecting-websocket";
import { receivedMatchState } from "../store/match-actions";

const MESSAGE_TYPES = {
  MATCH_STATE: "matchState",
};

export const connectToMatchStateWebSocket = () =>
  new ReconnectingWebSocket("ws://localhost:7000/match");

export const toAction = (webSocketMessageEvent) => {
  const message = JSON.parse(webSocketMessageEvent.data);
  switch (message.type) {
    case MESSAGE_TYPES.MATCH_STATE:
      return receivedMatchState(message.payload);
    default:
      console.error(
        "Unknown message type for message event: " + webSocketMessageEvent
      );
      return undefined;
  }
};
