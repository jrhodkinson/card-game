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
