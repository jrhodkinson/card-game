import Immutable from "seamless-immutable";
import {
  PLAYED_CARD,
  PURCHASED_CARD,
  SELECTED_CARD_REQUIRING_TARGET,
} from "./play-actions";

export const PLAY_STATE = "play";

export const defaultState = Immutable({
  pendingCardEntityId: undefined,
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case SELECTED_CARD_REQUIRING_TARGET:
      return state.set("pendingCardEntityId", action.cardEntityId);
    case PLAYED_CARD:
    case PURCHASED_CARD:
      return defaultState;
    default:
      return state;
  }
};
