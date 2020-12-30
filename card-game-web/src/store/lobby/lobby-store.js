import Immutable from "seamless-immutable";
import {
  getActiveMatchCount,
  getQueueStatus,
  postJoinQueue,
  postLeaveQueue,
} from "../../gateway/lobby";
import { LOGGED_OUT } from "../account/account-store";

export const LOBBY_STATE = "lobby";
export const NAMESPACE = "lobby";

export const defaultState = Immutable({
  initialised: false,
  matchId: undefined,
  matchPoller: undefined,
  queueing: false,
  activeMatches: 0,
  gameOffline: false,
});

export const RECEIVED_ACTIVE_MATCH_COUNT = `${NAMESPACE}/RECEIVED_ACTIVE_MATCH_COUNT`;
export const RECEIVED_GAME_OFFLINE = `${NAMESPACE}/RECEIVED_GAME_OFFLINE`;
export const RECEIVED_MATCH_ID = `${NAMESPACE}/RECEIVED_MATCH_ID`;
export const RECEIVED_NO_MATCH_ID = `${NAMESPACE}/RECEIVED_NO_MATCH_ID`;
export const STARTED_MATCH_POLLER = `${NAMESPACE}/STARTED_MATCH_POLLER`;
export const IN_QUEUE = `${NAMESPACE}/IN_QUEUE`;
export const LEFT_QUEUE = `${NAMESPACE}/LEFT_QUEUE`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_ACTIVE_MATCH_COUNT:
      return state.set("activeMatches", action.activeMatches);
    case RECEIVED_MATCH_ID:
      return state
        .set("matchId", action.matchId)
        .set("initialised", true)
        .set("matchPoller", undefined)
        .set("queueing", false);
    case RECEIVED_NO_MATCH_ID:
      return state.set("initialised", true);
    case IN_QUEUE:
      return state.set("matchId", undefined).set("queueing", true);
    case STARTED_MATCH_POLLER:
      return state.set("matchPoller", action.matchPoller);
    case RECEIVED_GAME_OFFLINE:
      return state.set("gameOffline", true);
    case LEFT_QUEUE:
    case LOGGED_OUT:
      if (state.matchPoller) {
        clearInterval(state.matchPoller);
      }
      return state
        .set("matchId", undefined)
        .set("initialised", true)
        .set("matchPoller", undefined)
        .set("queueing", false);
    default:
      return state;
  }
};

export const queue = () => (dispatch) => {
  postJoinQueue().then(() => {
    dispatch({ type: IN_QUEUE });
    dispatch(fetchQueueStatus());
  });
};

export const leaveQueue = () => (dispatch) => {
  postLeaveQueue().then(() => {
    dispatch({ type: LEFT_QUEUE });
  });
};

export const fetchActiveMatchCount = () => (dispatch) => {
  getActiveMatchCount()
    .then(({ data }) => {
      dispatch({
        type: RECEIVED_ACTIVE_MATCH_COUNT,
        activeMatches: data.activeMatches,
      });
    })
    .catch(({ response }) => {
      if (response.status === 404) {
        dispatch({ type: RECEIVED_GAME_OFFLINE });
      }
    });
};

export const fetchQueueStatus = () => (dispatch, getState) => {
  if (!getState()[LOBBY_STATE].matchPoller) {
    const matchPoller = setInterval(() => {
      getQueueStatus()
        .then(({ data }) => {
          if (data.type === "in match") {
            dispatch({ type: RECEIVED_MATCH_ID, matchId: data.matchId });
            clearInterval(matchPoller);
          }
          dispatch({ type: RECEIVED_NO_MATCH_ID });
          if (data.type === "in queue") {
            dispatch({ type: IN_QUEUE });
          }
        })
        .catch(({ response }) => {
          if (response.status === 404) {
            dispatch({ type: RECEIVED_NO_MATCH_ID });
            clearInterval(matchPoller);
          }
        });
    }, 1500);
    dispatch({ type: STARTED_MATCH_POLLER, matchPoller: matchPoller });
  }
};

export const selectActiveMatches = (store) =>
  store[LOBBY_STATE].activeMatches || 0;
export const selectIsQueueing = (store) => store[LOBBY_STATE].queueing;
export const selectCurrentMatchId = (store) => store[LOBBY_STATE].matchId;
export const selectHaveInitialisedMatchId = (store) =>
  store[LOBBY_STATE].initialised;
export const selectIsGameOffline = (store) => store[LOBBY_STATE].gameOffline;
