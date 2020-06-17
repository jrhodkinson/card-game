import React from "react";
import styled from "styled-components";
import Card from "./card/Card";

const Wrapper = styled.div`
  display: flex;
`;

const Cards = ({ cards }) => {
  return (
    <Wrapper>
      {cards.map((card) => (
        <Card card={card} />
      ))}
    </Wrapper>
  );
};

export default Cards;
