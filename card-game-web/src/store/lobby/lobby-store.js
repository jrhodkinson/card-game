import Immutable from "seamless-immutable";
import { getCurrentMatch, postJoinQueue } from "../../gateway/lobby";

export const LOBBY_STATE = "lobby";
export const NAMESPACE = "lobby";

export const defaultState = Immutable({
  matchId: undefined,
  matchPoller: undefined,
});

export const RECEIVED_MATCH_ID = `${NAMESPACE}/RECEIVED_MATCH_ID`;
export const STARTED_MATCH_POLLER = `${NAMESPACE}/STARTED_MATCH_POLLER`;
export const JOINED_QUEUE = `${NAMESPACE}/JOINED_QUEUE`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_MATCH_ID:
      return state.set("matchId", action.matchId).set("matchPoller", undefined);
    case JOINED_QUEUE:
      return state.set("matchId", undefined);
    case STARTED_MATCH_POLLER:
      return state.set("matchPoller", action.matchPoller);
    default:
      return state;
  }
};

export const queue = () => (dispatch) => {
  postJoinQueue().then(() => {
    dispatch({ type: JOINED_QUEUE });
    dispatch(fetchCurrentMatch());
  });
};

export const fetchCurrentMatch = () => (dispatch, getState) => {
  if (!getState()[LOBBY_STATE].matchPoller) {
    const matchPoller = setInterval(() => {
      getCurrentMatch().then((response) => {
        dispatch({ type: RECEIVED_MATCH_ID, matchId: response.data });
        clearInterval(matchPoller);
      });
    }, 500);
    dispatch({ type: STARTED_MATCH_POLLER, matchPoller: matchPoller });
  }
};

export const selectCurrentMatchId = (store) => store[LOBBY_STATE].matchId;
