import React from "react";
import styled, { css } from "styled-components";
import useTooltip from "../common/useTooltip";

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

const cardNames = (cards) => cards.map((card) => card.name).join("<br />");

const Pile = ({ cards, name, tooltipHeader = null, align = "left" }) => {
  const { id, tooltip } = useTooltip({ fixed: false });
  let tooltipText = cardNames(cards);
  if (tooltipHeader && cards.length > 0) {
    tooltipText = tooltipHeader + "<br />" + tooltipText;
  }
  return (
    <Wrapper data-tip={tooltipText} data-multiline data-for={id} align={align}>
      {cards.length === 1
        ? `1 card in ${name}`
        : `${cards.length} cards in ${name}`}
      {tooltip}
    </Wrapper>
  );
};

export default Pile;
