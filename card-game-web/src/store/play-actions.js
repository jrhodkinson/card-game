import { buyCard, playCard } from "../gateway/ws";
import { getPendingCardInstanceId } from "./play-selector";

export const NAMESPACE = "match";

export const SELECTED_CARD_REQUIRING_TARGET = `${NAMESPACE}/SELECTED_CARD_REQUIRING_TARGET`;
export const PLAYED_CARD = `${NAMESPACE}/PLAYED_CARD`;
export const PURCHASED_CARD = `${NAMESPACE}/PURCHASED_CARD`;

export const selectedCardInHand = (card) => (dispatch) => {
  const { requiresTarget, instanceId } = card;
  if (requiresTarget) {
    dispatch({
      type: SELECTED_CARD_REQUIRING_TARGET,
      cardInstanceId: instanceId,
    });
  } else {
    playCard(instanceId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedTarget = (targetInstanceId) => (dispatch, getState) => {
  const pendingCardInstanceId = getPendingCardInstanceId(getState());
  if (pendingCardInstanceId) {
    playCard(pendingCardInstanceId, targetInstanceId);
    dispatch({ type: PLAYED_CARD });
  }
};

export const selectedCardInStorefront = (card) => (dispatch) => {
  const { instanceId } = card;
  buyCard(instanceId);
  dispatch({ type: PURCHASED_CARD });
};
