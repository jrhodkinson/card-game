import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  text-align: center;
`;

const SecondaryPlayerHand = ({ size }) => {
  return <Wrapper>{size} cards</Wrapper>;
};

export default SecondaryPlayerHand;
