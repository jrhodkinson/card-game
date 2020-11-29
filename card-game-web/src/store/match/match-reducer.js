import Immutable from "seamless-immutable";
import { JOINED_QUEUE } from "../lobby/lobby-store";
import { MATCH_ENDED, RECEIVED_MATCH_STATE } from "./match-actions";

export const MATCH_STATE = "match";

export const defaultState = Immutable({
  initialised: false,
  activeUser: "",
  players: {},
  currentTurn: {
    money: 0,
    playedCards: [],
  },
  storefront: {
    row: [],
  },
  winner: null,
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_MATCH_STATE:
      return state.merge(action.matchState).set("initialised", true);
    case MATCH_ENDED:
      return defaultState.set("winner", action.winner);
    case JOINED_QUEUE:
      return defaultState;
    default:
      return state;
  }
};
