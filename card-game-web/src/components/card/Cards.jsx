import React from "react";
import styled from "styled-components";
import Card from "./Card";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  overflow-y: auto;
  justify-content: center;

  // overflow hack
  margin: -10px 0 0 -10px;
  padding: 10px 0 0 10px;
`;

const Cards = ({
  cards,
  short = false,
  selectedCardEntityId = undefined,
  interactable = false,
  onCardClick = () => {},
}) => {
  return (
    <Wrapper>
      {cards.map((card) => {
        const { entityId } = card;
        return (
          <Card
            key={entityId}
            card={card}
            short={short}
            interactable={interactable}
            selected={selectedCardEntityId === entityId}
            onCardClick={onCardClick}
          />
        );
      })}
    </Wrapper>
  );
};

export default Cards;
