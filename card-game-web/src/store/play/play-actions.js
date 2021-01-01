import { buyCard, playCard } from "../../gateway/ws";
import { selectIsSpectating } from "../lobby/lobby-store";
import {
  selectDoesPendingCardRequireCardInHandTarget,
  selectDoesPendingCardRequireDamageableTarget,
  selectDoesPendingCardRequireStoreTarget,
  selectDoesPendingCardRequireTarget,
  selectPendingCardEntityId,
} from "./play-selector";

export const NAMESPACE = "match";

export const SELECTED_CARD_REQUIRING_TARGET = `${NAMESPACE}/SELECTED_CARD_REQUIRING_TARGET`;
export const CLEAR_PENDING_CARD = `${NAMESPACE}/CLEAR_PENDING_CARD`;
export const PLAYED_CARD = `${NAMESPACE}/PLAYED_CARD`;
export const PURCHASED_CARD = `${NAMESPACE}/PURCHASED_CARD`;

const targetType = (card) => {
  if (card.requiresDamageableTarget) {
    return "damageable";
  } else if (card.requiresStoreTarget) {
    return "store";
  } else if (card.requiresCardInHandTarget) {
    return "hand";
  }
};

const requiresTarget = (card) => targetType(card);

export const selectedCardInHand = (card) => (dispatch, getState) => {
  const state = getState();
  if (selectIsSpectating(state)) {
    return;
  }
  const { entityId } = card;
  const pendingCardEntityId = selectPendingCardEntityId(state);
  if (entityId === pendingCardEntityId) {
    dispatch({ type: CLEAR_PENDING_CARD });
  } else if (selectDoesPendingCardRequireCardInHandTarget(state)) {
    playCard(pendingCardEntityId, entityId);
    dispatch({ type: PLAYED_CARD });
  } else if (selectDoesPendingCardRequireTarget(state)) {
    dispatch({ type: CLEAR_PENDING_CARD });
  } else if (requiresTarget(card)) {
    dispatch({
      type: SELECTED_CARD_REQUIRING_TARGET,
      targetType: targetType(card),
      cardEntityId: entityId,
    });
  } else {
    playCard(entityId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedDamageableTarget = (targetEntityId) => (
  dispatch,
  getState
) => {
  if (selectIsSpectating(getState())) {
    return;
  }
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
  if (selectIsSpectating(getState())) {
    return;
  }
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
