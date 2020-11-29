import React from "react";
import styled from "styled-components";
import ReactTooltip from "react-tooltip";

const Wrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 10px;
  text-align: right;
`;

const cardNames = (cards) => cards.map((card) => card.name).join("<br />");

const Pile = ({ cards, name }) => {
  return (
    <Wrapper data-tip={cardNames(cards)}>
      {cards.length === 0 ? `Empty ${name}` : `${cards.length} in ${name}`}
      <ReactTooltip multiline />
    </Wrapper>
  );
};

export default Pile;
