import Immutable from "seamless-immutable";
import { MATCH_ENDED, RECEIVED_MATCH_STATE } from "./match-actions";

export const MATCH_STATE = "match";

export const defaultPlayer = Immutable({
  entityId: "",
  username: "",
  health: 0,
  structures: [],
  hand: [],
  deckSize: 0,
  discardPile: [],
});

export const defaultState = Immutable({
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
      return state.merge(action.matchState);
    case MATCH_ENDED:
      return defaultState.set("winner", action.winner);
    default:
      return state;
  }
};
