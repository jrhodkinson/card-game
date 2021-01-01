import React from "react";
import { card, image } from "./styles/dimensions";
import { CSSTransition, TransitionGroup } from "react-transition-group";
import styled, { keyframes, css } from "styled-components";
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

  min-height: ${({ short }) =>
    short
      ? card.FULL_HEIGHT - image.HEIGHT - card.PADDING
      : card.FULL_HEIGHT}px;
`;

const fadeIn = keyframes`
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
`;

const fadeOut = keyframes`
  0% {
    opacity: 1;
  }

  100% {
    opacity: 0;
  }
`;

const AnimationWrapper = styled.div`
  ${({ animateEntry }) => {
    if (animateEntry) {
      return css`
        &.enter {
          opacity: 0;
        }

        &.enter-active {
          animation: ${fadeIn} ${animationDuration - animationDelay}ms
            ${animationDelay}ms;
        }

        &.exit,
        .exit-active {
          display: none;
        }
      `;
    }
  }};

  ${({ animateExit }) => {
    if (animateExit) {
      return css`
        .exit {
          opacity: 0;
        }

        .exit-active {
          animation: ${fadeOut} ${animationDuration - animationDelay}ms
            ${animationDelay}ms;
        }
      `;
    }
  }};
`;

const Cards = ({
  cards,
  short = false,
  selectedCardEntityId = undefined,
  isInteractable = () => false,
  animateEntry = false,
  animateExit = false,
  onCardClick = () => {},
  shaking = () => false,
  displayCost = true,
}) => {
  const cardComponent = (card) => (
    <Card
      key={card.entityId}
      card={card}
      short={short}
      isInteractable={isInteractable}
      selected={selectedCardEntityId === card.entityId}
      onCardClick={onCardClick}
      shaking={shaking(card)}
      displayCost={displayCost}
    />
  );

  if (animateEntry || animateExit) {
    return (
      <Wrapper short={short}>
        <TransitionGroup component={null}>
          {cards.map((card) => (
            <CSSTransition key={card.entityId} timeout={animationDuration}>
              <AnimationWrapper
                animateEntry={animateEntry}
                animateExit={animateExit}
              >
                {cardComponent(card)}
              </AnimationWrapper>
            </CSSTransition>
          ))}
        </TransitionGroup>
      </Wrapper>
    );
  }

  return <Wrapper>{cards.map((card) => cardComponent(card))}</Wrapper>;
};

export default Cards;
