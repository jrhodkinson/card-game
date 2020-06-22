import React, { useState } from "react";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const [selectedCardInstanceId, setSelectedCardInstanceId] = useState();
  const handleCardClick = (card) => {
    setSelectedCardInstanceId((selectedCardInstanceId) =>
      selectedCardInstanceId === card.instanceId ? undefined : card.instanceId
    );
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
