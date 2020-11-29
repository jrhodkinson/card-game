import React from "react";
import { CSSTransition, TransitionGroup } from "react-transition-group";
import styled from "styled-components";
import * as c from "../colors";

const healthDiameter = 32;
const animationTime = 1100;

const Wrapper = styled.div`
  position: relative;
  width: ${healthDiameter}px;
  height: ${healthDiameter}px;
`;

const AnimatedHealth = styled.div`
  position: absolute;
  background-color: ${c.red};
  color: ${c.textOnRed};
  text-align: center;
  line-height: ${healthDiameter}px;
  width: ${healthDiameter}px;
  height: ${healthDiameter}px;
  border-radius: ${healthDiameter / 4}px;
  left: 0;
  top: 0;

  @keyframes focus {
    20%,
    60% {
      transform: scale(1.15);
      background-color: ${c.accentRed};
      border-radius: ${healthDiameter / 8}px;
      font-size: 1.1em;
    }
    100% {
      transform: scale(1);
      background-color: ${c.red};
    }
  }

  ${({ state }) => {
    if (state === "entering") {
      return `
        animation: focus ${animationTime / 1000}s ease-in-out;
        z-index: 2;
        font-weight: 500;
      `;
    }
  }};
`;

const Health = ({ health }) => {
  return (
    <Wrapper>
      <TransitionGroup component={null}>
        <CSSTransition key={health} timeout={animationTime} exit={false}>
          {(state) => <AnimatedHealth state={state}>{health}</AnimatedHealth>}
        </CSSTransition>
      </TransitionGroup>
    </Wrapper>
  );
};

export default Health;
