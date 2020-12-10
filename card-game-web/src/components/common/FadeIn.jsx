import React from "react";
import styled, { css, keyframes } from "styled-components";

const fadeIn = keyframes`
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
`;

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  opacity: 0;

  ${({ delay, length }) => {
    return css`
      animation: ${fadeIn} ${length}s ease ${delay}s forwards;
    `;
  }};
`;

const FadeIn = ({ fast = false, children }) => {
  const parameters = fast ? { delay: 0, length: 0.4 } : { delay: 1, length: 1 };
  return <Wrapper {...parameters}>{children}</Wrapper>;
};

export default FadeIn;
