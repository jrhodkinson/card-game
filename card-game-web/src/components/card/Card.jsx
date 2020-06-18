import React from "react";
import styled from "styled-components";
import CardDescription from "./CardDescription";
import CardHeader from "./CardHeader";
import CardImage from "./CardImage";

const colors = {
  RED: "#FBB",
  YELLOW: "#FFC",
  BLUE: "#CCF",
  WHITE: "#FFF",
  GREEN: "#CFC",
  CYAN: "#CFF",
};

const Wrapper = styled.div`
  border: 1px solid black;
  box-shadow: rgba(0, 0, 0, 0.2) 0 2px 1px -1px, rgba(0, 0, 0, 0.14) 0 1px 1px 0,
    rgba(0, 0, 0, 0.12) 0 1px 2px 0;
  margin: 10px;
  min-width: 180px;
  width: 180px;

  background-color: ${(props) => colors[props.background] || "#F7F"};
`;

const Card = ({ card }) => {
  return (
    <Wrapper background={card.color}>
      <CardHeader name={card.name} cost={card.cost} />
      <CardImage />
      <CardDescription description={card.description} />
    </Wrapper>
  );
};

export default Card;
