import Immutable from "seamless-immutable";
import { RECEIVED_MATCH_STATE } from "./match-actions";

export const MATCH_STATE = "match";

export const defaultState = Immutable({
  activePlayer: {
    instanceId: "",
    username: "",
    health: 0,
    structures: [],
    hand: [],
    deckSize: 0,
    discardPile: [],
  },
  inactivePlayer: {
    instanceId: "",
    username: "",
    health: 0,
    structures: [],
    hand: [],
    deckSize: 0,
    discardPile: [],
  },
  currentTurn: {
    money: 0,
    playedCards: [],
  },
  storefront: {
    row: [],
  },
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_MATCH_STATE:
      return state.merge(action.matchState);
    default:
      return state;
  }
};
