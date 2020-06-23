import ReconnectingWebSocket from "reconnecting-websocket";
import { receivedMatchState } from "../store/match-actions";
import { receivedPing } from "../store/socket-actions";

let ws;

const send = (type, payload) => {
  ws.send(JSON.stringify({ type, payload }));
};

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
    send(PONG, message.payload);
  }
  const action = toAction(message);
  if (action) {
    dispatch(action);
  }
};

export const connectToMatchWebSocket = (dispatch) => {
  ws = new ReconnectingWebSocket("ws://localhost:7000/match/Villain");
  ws.onmessage = handleMessage(dispatch, ws);
  return ws.close;
};

export const playCard = (cardInstanceId) => {
  send("card/play", cardInstanceId);
};
