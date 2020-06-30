import React from "react";
import styled from "styled-components";
import Card from "./Card";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  overflow-y: auto;
  max-width: 100%;

  // overflow hack
  margin: -10px 0 0 -10px;
  padding: 10px 0 0 10px;
`;

const Cards = ({
  cards,
  selectedCardInstanceId = undefined,
  interactable = false,
  onCardClick = () => {},
}) => {
  return (
    <Wrapper>
      {cards.map((card) => {
        const { instanceId } = card;
        return (
          <Card
            key={instanceId}
            card={card}
            interactable={interactable}
            selected={selectedCardInstanceId === instanceId}
            onCardClick={onCardClick}
          />
        );
      })}
    </Wrapper>
  );
};

export default Cards;
