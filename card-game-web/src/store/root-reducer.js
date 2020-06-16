import { combineReducers } from "redux";
import matchReducer, { MATCH_STATE } from "./match-reducer";

export default combineReducers({
  [MATCH_STATE]: matchReducer,
});
