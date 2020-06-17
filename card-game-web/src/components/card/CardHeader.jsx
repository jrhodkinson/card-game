import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  font-weight: bold;
`;

const CardHeader = ({ name }) => {
  return <Wrapper>{name}</Wrapper>;
};

export default CardHeader;
