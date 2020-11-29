import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 0 10px;
  text-align: right;
`;

const Deck = ({ size }) => <Wrapper>{size} in deck</Wrapper>;

export default Deck;
