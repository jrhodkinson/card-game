import Immutable from "seamless-immutable";
import { RECEIVED_MATCH_STATE } from "./match-actions";

export const MATCH_STATE = "match";

export const defaultState = Immutable({
  activePlayer: {
    username: "",
    health: 0,
    structures: [],
    hand: [],
    deckSize: 0,
    discardPileSize: 0,
  },
  inactivePlayer: {
    username: "",
    health: 0,
    structures: [],
    hand: [],
    deckSize: 0,
    discardPileSize: 0,
  },
  currentTurn: {
    money: 0,
    playedCards: [],
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
