import { buyCard, playCard } from "../../gateway/ws";
import {
  selectDoesPendingCardRequireDamageableTarget,
  selectDoesPendingCardRequireStoreTarget,
  selectPendingCardEntityId,
} from "./play-selector";

export const NAMESPACE = "match";

export const SELECTED_CARD_REQUIRING_DAMAGEABLE_TARGET = `${NAMESPACE}/SELECTED_CARD_REQUIRING_DAMAGEABLE_TARGET`;
export const SELECTED_CARD_REQUIRING_STORE_TARGET = `${NAMESPACE}/SELECTED_CARD_REQUIRING_STORE_TARGET`;
export const PLAYED_CARD = `${NAMESPACE}/PLAYED_CARD`;
export const PURCHASED_CARD = `${NAMESPACE}/PURCHASED_CARD`;

export const selectedCardInHand = (card) => (dispatch) => {
  const { entityId } = card;
  if (card.requiresDamageableTarget) {
    dispatch({
      type: SELECTED_CARD_REQUIRING_DAMAGEABLE_TARGET,
      cardEntityId: entityId,
    });
  } else if (card.requiresStoreTarget) {
    dispatch({
      type: SELECTED_CARD_REQUIRING_STORE_TARGET,
      cardEntityId: entityId,
    });
  } else {
    playCard(entityId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedTarget = (targetEntityId) => (dispatch, getState) => {
  const requiresDamageableTarget = selectDoesPendingCardRequireDamageableTarget(
    getState()
  );
  if (requiresDamageableTarget) {
    const pendingCardEntityId = selectPendingCardEntityId(getState());
    playCard(pendingCardEntityId, targetEntityId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedCardInStorefront = (card) => (dispatch, getState) => {
  const requireStoreTarget = selectDoesPendingCardRequireStoreTarget(
    getState()
  );
  const { entityId } = card;
  if (requireStoreTarget) {
    const pendingCardEntityId = selectPendingCardEntityId(getState());
    playCard(pendingCardEntityId, entityId);
    dispatch({ type: PLAYED_CARD });
  } else {
    buyCard(entityId);
    dispatch({ type: PURCHASED_CARD });
  }
};
