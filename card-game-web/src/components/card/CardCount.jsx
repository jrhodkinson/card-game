import React from "react";
import styled, { css } from "styled-components";

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 0 10px;

  ${({ align }) => {
    if (align === "right") {
      return css`
        text-align: right;
        justify-content: flex-end;
      `;
    }
  }};
`;

const CardCount = ({ size, name, align = "left" }) => {
  return (
    <Wrapper align={align}>
      {size === 1 ? `1 card in ${name}` : `${size} cards in ${name}`}
    </Wrapper>
  );
};

export default CardCount;
