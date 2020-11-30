import React from "react";
import { CSSTransition, TransitionGroup } from "react-transition-group";
import styled from "styled-components";
import Card from "./Card";

const animationDelay = 200;
const animationDuration = 600;

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  overflow-y: auto;
  justify-content: center;

  // overflow hack
  margin: -10px 0 0 -10px;
  padding: 10px 0 0 10px;
`;

const AnimationWrapper = styled.div`
  @keyframes fadeIn {
    0% {
      opacity: 0;
    }

    100% {
      opacity: 1;
    }
  }
  &.enter {
    opacity: 0;
  }
  &.enter-active {
    animation: fadeIn ${animationDuration - animationDelay}ms
      ${animationDelay}ms;
  }
  &.exit,
  .exit-active {
    display: none;
  }
`;

const Cards = ({
  cards,
  short = false,
  selectedCardEntityId = undefined,
  isInteractable = () => false,
  animateEntry = false,
  onCardClick = () => {},
}) => {
  const cardComponent = (card) => (
    <Card
      key={card.entityId}
      card={card}
      short={short}
      isInteractable={isInteractable}
      selected={selectedCardEntityId === card.entityId}
      onCardClick={onCardClick}
    />
  );

  if (animateEntry) {
    return (
      <Wrapper>
        <TransitionGroup component={null}>
          {cards.map((card) => (
            <CSSTransition key={card.entityId} timeout={animationDuration}>
              <AnimationWrapper>{cardComponent(card)}</AnimationWrapper>
            </CSSTransition>
          ))}
        </TransitionGroup>
      </Wrapper>
    );
  }

  return <Wrapper>{cards.map((card) => cardComponent(card))}</Wrapper>;
};

export default Cards;
