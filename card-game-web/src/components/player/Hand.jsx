import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { selectedCardInHand } from "../../store/play-actions";
import { getPendingCardEntityId } from "../../store/play-selector";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const dispatch = useDispatch();
  const pendingCardEntityId = useSelector(getPendingCardEntityId);
  const handleCardClick = (card) => dispatch(selectedCardInHand(card));
  return (
    <Cards
      cards={hand}
      interactable
      onCardClick={handleCardClick}
      selectedCardEntityId={pendingCardEntityId}
    />
  );
};

export default Hand;
