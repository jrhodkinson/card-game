import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
`;

const CardDescription = ({ description }) => {
  return <Wrapper>{description}</Wrapper>;
};

export default CardDescription;
