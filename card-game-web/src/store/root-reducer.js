import { combineReducers } from "redux";
import accountReducer, { ACCOUNT_STATE } from "./account/account-store";
import lobbyReducer, { LOBBY_STATE } from "./lobby/lobby-store";
import matchReducer, { MATCH_STATE } from "./match/match-reducer";
import playReducer, { PLAY_STATE } from "./play/play-reducer";
import socketReducer, { SOCKET_STATE } from "./socket/socket-reducer";

export default combineReducers({
  [ACCOUNT_STATE]: accountReducer,
  [LOBBY_STATE]: lobbyReducer,
  [MATCH_STATE]: matchReducer,
  [PLAY_STATE]: playReducer,
  [SOCKET_STATE]: socketReducer,
});
