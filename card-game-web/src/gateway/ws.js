import ReconnectingWebSocket from "reconnecting-websocket";
import { receivedMatchState } from "../store/match-actions";
import { receivedPing } from "../store/socket-actions";

const PING = "ping";
const PONG = "pong";

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

const handleMessage = (dispatch, ws) => (event) => {
  const message = JSON.parse(event.data);
  if (message.type === PING) {
    ws.send(JSON.stringify({ type: PONG, payload: message.payload }));
  }
  const action = toAction(message);
  if (action) {
    dispatch(action);
  }
};

export const connectToMatchWebSocket = (dispatch) => {
  const ws = new ReconnectingWebSocket("ws://localhost:7000/match");
  ws.onmessage = handleMessage(dispatch, ws);
  return ws.close;
};
