import React from "react";
import CardCount from "./CardCount";
import Pile from "./Pile";

const Deck = ({ cards, size }) => {
  if (cards) {
    return <Pile cards={cards} name="deck" tooltipHeader="(alphabetical)" />;
  }
  return <CardCount size={size} name="deck" />;
};

export default Deck;
