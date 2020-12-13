import ReconnectingWebSocket from "reconnecting-websocket";
import {
  matchEnded,
  receivedMatchState,
  turnEnded,
  turnWillEndAt,
} from "../store/match/match-actions";
import { receivedPing } from "../store/socket/socket-actions";

const PING = "ping";
const PONG = "pong";

let ws;

const MESSAGE_ACTION_CREATORS = {
  ping: (payload) => receivedPing(payload),
  matchState: (payload) => receivedMatchState(payload),
  turnWillEndAt: (payload) => turnWillEndAt(payload),
  turnEnded: (payload) => turnEnded(payload),
  matchEnded: (payload) => matchEnded(payload),
};

const send = (type, payload) => {
  let message;
  if (payload === undefined) {
    message = JSON.stringify({ type });
  } else {
    message = JSON.stringify({ type, payload });
  }
  ws.send(message);
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
  const action = toAction(message);
  if (action) {
    dispatch(action);
  }
};

export const connectToMatchWebSocket = (dispatch, matchId) => {
  ws = new ReconnectingWebSocket(
    `${process.env.REACT_APP_MATCH_WEBSOCKET_PATH}${matchId}`
  );
  ws.onmessage = handleMessage(dispatch);
  return () => ws.close(1000, "disconnected");
};

export const endTurn = () => {
  send("turn/end");
};

export const playCard = (cardEntityId, targetEntityId) => {
  send("card/play", { card: cardEntityId, target: targetEntityId });
};

export const buyCard = (cardEntityId) => {
  send("card/buy", cardEntityId);
};
