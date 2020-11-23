import Immutable from "seamless-immutable";
import { getCurrentMatch } from "../../gateway/lobby";

export const LOBBY_STATE = "lobby";
export const NAMESPACE = "lobby";

export const defaultState = Immutable({
  matchId: undefined,
});

export const RECEIVED_MATCH_ID = `${NAMESPACE}/RECEIVED_MATCH_ID`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_MATCH_ID:
      return state.set("matchId", action.matchId);
    default:
      return state;
  }
};

export const fetchCurrentMatch = () => (dispatch) => {
  const matchPoller = setInterval(() => {
    getCurrentMatch().then((response) => {
      dispatch({ type: RECEIVED_MATCH_ID, matchId: response.data });
      clearInterval(matchPoller);
    });
  }, 5000);
};

export const selectCurrentMatchId = (store) => store[LOBBY_STATE].matchId;
