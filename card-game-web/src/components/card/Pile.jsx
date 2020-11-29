import React from "react";
import styled from "styled-components";
import useTooltip from "../common/useTooltip";

const Wrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 10px;
  text-align: right;
`;

const cardNames = (cards) => cards.map((card) => card.name).join("<br />");

const Pile = ({ cards, name }) => {
  const { id, tooltip } = useTooltip();
  return (
    <Wrapper data-tip={cardNames(cards)} data-multiline data-for={id}>
      {cards.length === 0 ? `Empty ${name}` : `${cards.length} in ${name}`}
      {tooltip}
    </Wrapper>
  );
};

export default Pile;
