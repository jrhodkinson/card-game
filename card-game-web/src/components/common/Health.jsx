import React from "react";
import { CSSTransition, TransitionGroup } from "react-transition-group";
import styled from "styled-components";
import * as c from "../styles/colors";

const healthDiameter = 32;
const animationTime = 800;

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
    20% {
      transform: scale(1.3);
      background-color: ${c.accentRed};
      border-radius: ${healthDiameter / 6}px;
      font-size: 1.1em;
    }
  }

  ${({ state }) => {
    if (state === "entering") {
      return `
        animation: focus ${animationTime / 1000}s ease-in-out;
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
