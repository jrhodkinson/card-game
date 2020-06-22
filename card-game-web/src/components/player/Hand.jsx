import React, { useState } from "react";
import { playCard } from "../../gateway/ws";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const [selectedCardInstanceId, setSelectedCardInstanceId] = useState(
    undefined
  );
  const handleCardClick = (card) => {
    const { instanceId } = card;
    // setSelectedCardInstanceId((selectedCardInstanceId) =>
    //   selectedCardInstanceId === instanceId ? undefined : instanceId
    // );
    playCard(instanceId);
  };
  return (
    <Cards
      cards={hand}
      selectable
      selectedCardInstanceId={selectedCardInstanceId}
      onCardClick={handleCardClick}
    />
  );
};

export default Hand;
