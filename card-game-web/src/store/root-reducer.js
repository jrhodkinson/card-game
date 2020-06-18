import { combineReducers } from "redux";
import matchReducer, { MATCH_STATE } from "./match-reducer";
import socketReducer, { SOCKET_STATE } from "./socket-reducer";

export default combineReducers({
  [MATCH_STATE]: matchReducer,
  [SOCKET_STATE]: socketReducer,
});
