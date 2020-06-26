import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { selectedCardInHand } from "../../store/play-actions";
import { getPendingCardInstanceId } from "../../store/play-selector";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const dispatch = useDispatch();
  const pendingCardInstanceId = useSelector(getPendingCardInstanceId);
  const handleCardClick = (card) => dispatch(selectedCardInHand(card));
  return (
    <Cards
      cards={hand}
      interactable
      onCardClick={handleCardClick}
      selectedCardInstanceId={pendingCardInstanceId}
    />
  );
};

export default Hand;
