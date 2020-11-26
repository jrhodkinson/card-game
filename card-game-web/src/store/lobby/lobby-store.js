import Immutable from "seamless-immutable";
import { getCurrentMatch, postJoinQueue } from "../../gateway/lobby";

export const LOBBY_STATE = "lobby";
export const NAMESPACE = "lobby";

export const defaultState = Immutable({
  matchId: undefined,
  queueing: false,
});

export const RECEIVED_MATCH_ID = `${NAMESPACE}/RECEIVED_MATCH_ID`;
export const JOINED_QUEUE = `${NAMESPACE}/JOINED_QUEUE`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_MATCH_ID:
      return state.set("matchId", action.matchId).set("queueing", false);
    case JOINED_QUEUE:
      return state.set("queueing", true).set("matchId", undefined);
    default:
      return state;
  }
};

export const queue = () => (dispatch) => {
  postJoinQueue().then(() => {
    dispatch({ type: JOINED_QUEUE });
  });
};

export const fetchCurrentMatch = () => (dispatch) => {
  const matchPoller = setInterval(() => {
    getCurrentMatch().then((response) => {
      dispatch({ type: RECEIVED_MATCH_ID, matchId: response.data });
      clearInterval(matchPoller);
    });
  }, 2000);
};

export const selectCurrentMatchId = (store) => store[LOBBY_STATE].matchId;
export const isQueueing = (store) => store[LOBBY_STATE].queueing;
