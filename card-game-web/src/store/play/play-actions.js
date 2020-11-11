import { buyCard, playCard } from "../../gateway/ws";
import { selectPendingCardEntityId } from "./play-selector";

export const NAMESPACE = "match";

export const SELECTED_CARD_REQUIRING_TARGET = `${NAMESPACE}/SELECTED_CARD_REQUIRING_TARGET`;
export const PLAYED_CARD = `${NAMESPACE}/PLAYED_CARD`;
export const PURCHASED_CARD = `${NAMESPACE}/PURCHASED_CARD`;

export const selectedCardInHand = (card) => (dispatch) => {
  const { requiresTarget, entityId } = card;
  if (requiresTarget) {
    dispatch({
      type: SELECTED_CARD_REQUIRING_TARGET,
      cardEntityId: entityId,
    });
  } else {
    playCard(entityId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedTarget = (targetEntityId) => (dispatch, getState) => {
  const pendingCardEntityId = selectPendingCardEntityId(getState());
  if (pendingCardEntityId) {
    playCard(pendingCardEntityId, targetEntityId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedCardInStorefront = (card) => (dispatch) => {
  const { entityId } = card;
  buyCard(entityId);
  dispatch({ type: PURCHASED_CARD });
};
