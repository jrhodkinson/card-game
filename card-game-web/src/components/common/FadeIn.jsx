import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  opacity: 0;

  @keyframes fadeIn {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }

  animation: ${({ delay, length }) => {
    return `fadeIn ${length}s ease ${delay}s forwards;`;
  }};
`;

const FadeIn = ({ fast = false, children }) => {
  const parameters = fast
    ? { delay: 0, length: 0.4 }
    : { delay: 0.4, length: 0.8 };
  return <Wrapper {...parameters}>{children}</Wrapper>;
};

export default FadeIn;
