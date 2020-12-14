import Immutable from "seamless-immutable";
import { LOGGED_OUT } from "../account/account-store";
import { IN_QUEUE } from "../lobby/lobby-store";
import { MATCH_ENDED, TURN_ENDED } from "../match/match-actions";
import {
  PLAYED_CARD,
  PURCHASED_CARD,
  SELECTED_CARD_REQUIRING_DAMAGEABLE_TARGET,
  SELECTED_CARD_REQUIRING_STORE_TARGET,
} from "./play-actions";

export const PLAY_STATE = "play";

export const defaultState = Immutable({
  pendingCardEntityId: undefined,
  expecting: undefined,
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case SELECTED_CARD_REQUIRING_DAMAGEABLE_TARGET:
      if (action.cardEntityId === state.pendingCardEntityId) {
        return defaultState;
      }
      return state
        .set("pendingCardEntityId", action.cardEntityId)
        .set("expecting", "damageable");
    case SELECTED_CARD_REQUIRING_STORE_TARGET:
      if (action.cardEntityId === state.pendingCardEntityId) {
        return defaultState;
      }
      return state
        .set("pendingCardEntityId", action.cardEntityId)
        .set("expecting", "store");
    case TURN_ENDED:
    case PLAYED_CARD:
    case PURCHASED_CARD:
    case MATCH_ENDED:
    case IN_QUEUE:
    case LOGGED_OUT:
      return defaultState;
    default:
      return state;
  }
};
