import ReconnectingWebSocket from "reconnecting-websocket";
import { receivedMatchState } from "../store/match-actions";
import { receivedPing } from "../store/socket-actions";

const PING = "ping";
const PONG = "pong";

let ws;

const send = (type, payload) => {
  let message;
  if (payload === undefined) {
    message = JSON.stringify({ type });
  } else {
    message = JSON.stringify({ type, payload });
  }
  if (type !== PONG) {
    console.log(`[ws] TX: ${message}`);
  }
  ws.send(message);
};

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

const handleMessage = (dispatch) => (event) => {
  const message = JSON.parse(event.data);
  if (message.type === PING) {
    send(PONG, message.payload);
  }
  console.log(`[ws] RX: ${message}`);
  const action = toAction(message);
  if (action) {
    dispatch(action);
  }
};

export const connectToMatchWebSocket = (dispatch) => {
  ws = new ReconnectingWebSocket("ws://localhost:7000/match");
  ws.onmessage = handleMessage(dispatch);
  return ws.close;
};

export const login = (user) => {
  send("login", user);
};

export const endTurn = () => {
  send("turn/end");
};

export const playCard = (cardInstanceId, targetInstanceId) => {
  send("card/play", { card: cardInstanceId, target: targetInstanceId });
};

export const buyCard = (cardInstanceId) => {
  send("card/buy", cardInstanceId);
};
