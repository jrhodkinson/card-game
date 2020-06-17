import React from "react";
import styled from "styled-components";
import Card from "./card/Card";

const Wrapper = styled.div`
  background-color: blue;
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
