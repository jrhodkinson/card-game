import React from "react";
import styled from "styled-components";
import CardStump from "../card/CardStump";
import { card } from "../card/styles/dimensions";

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  min-height: ${card.STUMP_HEIGHT};
`;

const SecondaryPlayerHand = ({ size }) => (
  <Wrapper>
    {[...Array(size)].map((e, i) => (
      <CardStump key={i} />
    ))}
  </Wrapper>
);

export default SecondaryPlayerHand;
