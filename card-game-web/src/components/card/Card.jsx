import React from "react";
import styled from "styled-components";
import CardHeader from "./CardHeader";

const Wrapper = styled.div`
  background-color: red;
  margin: 10px;
`;

const Card = ({ card }) => {
  return (
    <Wrapper>
      <CardHeader name={card.name} />
      {card.description}
      {card.cost}
    </Wrapper>
  );
};

export default Card;
