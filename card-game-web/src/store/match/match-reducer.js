import Immutable from "seamless-immutable";
import { LOGGED_OUT } from "../account/account-store";
import { IN_QUEUE } from "../lobby/lobby-store";
import {
  MATCH_ENDED,
  RECEIVED_MATCH_STATE,
  TURN_WILL_END_AT,
} from "./match-actions";

export const MATCH_STATE = "match";

export const defaultState = Immutable({
  state: {
    activeUser: "",
    players: {},
    currentTurn: {
      money: 0,
      playedCards: [],
    },
    storefront: {
      row: [],
    },
  },
  initialised: false,
  winner: null,
  turnWillEndAt: Number.MAX_SAFE_INTEGER,
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_MATCH_STATE:
      return state.merge({ state: action.matchState }).set("initialised", true);
    case TURN_WILL_END_AT:
      return state.set("turnWillEndAt", action.time);
    case MATCH_ENDED:
      return defaultState.set("winner", action.winner).set("initialised", true);
    case IN_QUEUE:
    case LOGGED_OUT:
      return defaultState;
    default:
      return state;
  }
};
