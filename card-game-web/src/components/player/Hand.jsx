import React from "react";
import { useDispatch } from "react-redux";
import { selectedCardInHand } from "../../store/play-actions";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const dispatch = useDispatch();
  const handleCardClick = (card) => dispatch(selectedCardInHand(card));
  return <Cards cards={hand} selectable onCardClick={handleCardClick} />;
};

export default Hand;
