import { combineReducers } from "redux";
import matchReducer, { MATCH_STATE } from "./match-reducer";
import playReducer, { PLAY_STATE } from "./play-reducer";
import socketReducer, { SOCKET_STATE } from "./socket-reducer";

export default combineReducers({
  [MATCH_STATE]: matchReducer,
  [PLAY_STATE]: playReducer,
  [SOCKET_STATE]: socketReducer,
});
