import ReconnectingWebSocket from "reconnecting-websocket";
import { receivedMatchState } from "../store/match-actions";
import { receivedPing } from "../store/socket-actions";

const MESSAGE_ACTION_CREATORS = {
  ping: (payload) => receivedPing(payload),
  matchState: (payload) => receivedMatchState(payload),
};

const toAction = (message) => {
  if (
    !Object.prototype.hasOwnProperty.call(MESSAGE_ACTION_CREATORS, message.type)
  ) {
    console.error(
      `Unknown message type for message: ${JSON.stringify(message)}`
    );
    return undefined;
  }
  return MESSAGE_ACTION_CREATORS[message.type](message.payload);
};

export const connectToMatchWebSocket = (dispatch) => {
  const ws = new ReconnectingWebSocket("ws://localhost:7000/match");
  ws.onmessage = (event) => {
    const message = JSON.parse(event.data);
    const action = toAction(message);
    if (action) {
      dispatch(action);
    }
  };
  return ws.close;
};
