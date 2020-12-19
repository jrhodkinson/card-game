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
  const { id, tooltip } = useTooltip({ fixed: false });
  return (
    <>
      <Wrapper data-tip={cardNames(cards)} data-multiline data-for={id}>
        {cards.length === 1
          ? `1 card in ${name}`
          : `${cards.length} cards in ${name}`}
      </Wrapper>
      {tooltip}
    </>
  );
};

export default Pile;
