import React from "react";
import styled from "styled-components";
import Card from "./Card";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  overflow: auto;
  max-width: 100%;
`;

const Cards = ({
  cards,
  selectedCardInstanceId,
  selectable = false,
  onCardClick = () => {},
}) => {
  return (
    <Wrapper>
      {cards.map((card) => (
        <Card
          key={card.instanceId}
          card={card}
          selectable={selectable}
          selected={selectable && selectedCardInstanceId === card.instanceId}
          onCardClick={onCardClick}
        />
      ))}
    </Wrapper>
  );
};

export default Cards;
