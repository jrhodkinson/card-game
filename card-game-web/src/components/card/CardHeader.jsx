import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  border-bottom: 1px solid black;
  display: grid;
  height: 44px;
  grid-template-columns: 44px 1fr;
`;

const HeaderElement = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  line-height: 14px;
`;

const Cost = styled(HeaderElement)`
  background-color: #444;
  border-right: 1px solid black;
  color: white;
  font-weight: bold;
`;

const Name = styled(HeaderElement)`
  padding: 0 10px;
`;

const CardHeader = ({ name, cost }) => (
  <Wrapper>
    <Cost>{cost}</Cost>
    <Name>{name}</Name>
  </Wrapper>
);

export default CardHeader;
