import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  border-bottom: 1px solid black;
  display: grid;
  height: 36px;
  grid-template-columns: 36px 1fr;
  grid-gap: 5px;
`;

const HeaderElement = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
`;

const Cost = styled(HeaderElement)`
  background-color: #444;
  border-right: 1px solid black;
  color: white;
  font-weight: bold;
`;

const Name = HeaderElement;

const CardHeader = ({ name, cost }) => {
  return (
    <Wrapper>
      <Cost>{cost}</Cost>
      <Name>{name}</Name>
    </Wrapper>
  );
};

export default CardHeader;
