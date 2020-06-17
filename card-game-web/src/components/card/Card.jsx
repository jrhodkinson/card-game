import React from "react";
import styled from "styled-components";
import CardDescription from "./CardDescription";
import CardHeader from "./CardHeader";
import CardImage from "./CardImage";

const Wrapper = styled.div`
  border: 2px solid black;
  margin: 10px;
  width: 160px;
`;

const Card = ({ card }) => {
  return (
    <Wrapper>
      <CardHeader name={card.name} cost={card.cost} />
      <CardImage />
      <CardDescription description={card.description} />
    </Wrapper>
  );
};

export default Card;
