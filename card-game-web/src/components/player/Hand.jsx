import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { selectedCardInHand } from "../../store/play/play-actions";
import { selectPendingCardEntityId } from "../../store/play/play-selector";
import Cards from "../card/Cards";

const Hand = ({ hand, interactable }) => {
  const dispatch = useDispatch();
  const pendingCardEntityId = useSelector(selectPendingCardEntityId);
  const handleCardClick = (card) => {
    if (interactable) {
      dispatch(selectedCardInHand(card));
    }
  };
  return (
    <Cards
      cards={hand}
      isInteractable={(card) => interactable && card.isPlayable}
      onCardClick={handleCardClick}
      selectedCardEntityId={pendingCardEntityId}
    />
  );
};

export default Hand;
