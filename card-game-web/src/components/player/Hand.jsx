import React, { useState } from "react";
import Cards from "../card/Cards";

const Hand = ({ hand }) => {
  const [selectedCardInstanceId, setSelectedCardInstanceId] = useState(
    undefined
  );
  const handleCardClick = (card) => {
    const { instanceId } = card;
    setSelectedCardInstanceId((selectedCardInstanceId) =>
      selectedCardInstanceId === instanceId ? undefined : instanceId
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
