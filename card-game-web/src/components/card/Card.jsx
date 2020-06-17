import React, { useState } from "react";
import styled from "styled-components";
import CardDescription from "./CardDescription";
import CardHeader from "./CardHeader";
import CardImage from "./CardImage";

const Wrapper = styled.div`
  background-color: white;
  border: 1px solid black;
  box-shadow: rgba(0, 0, 0, 0.2) 0 2px 1px -1px, rgba(0, 0, 0, 0.14) 0 1px 1px 0,
    rgba(0, 0, 0, 0.12) 0 1px 2px 0;
  margin: 10px;
  width: 180px;
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
