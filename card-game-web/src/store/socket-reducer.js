import Immutable from "seamless-immutable";
import { RECEIVED_PING } from "./socket-actions";

export const SOCKET_STATE = "socket";

export const defaultState = Immutable({
  lastPing: -1,
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_PING:
      return state.set("lastPing", action.timestamp);
    default:
      return state;
  }
};
