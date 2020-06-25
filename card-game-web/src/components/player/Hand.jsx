import React, { useState } from "react";
import { playCard } from "../../gateway/ws";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const handleCardClick = (card) => {
    const { instanceId } = card;
    playCard(instanceId);
  };
  return <Cards cards={hand} selectable onCardClick={handleCardClick} />;
};

export default Hand;
