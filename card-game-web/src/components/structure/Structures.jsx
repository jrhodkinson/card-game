import React from "react";
import styled from "styled-components";
import Structure from "./Structure";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  overflow: auto;
  max-width: 100%;
`;

const Structures = ({ structures }) => {
  return (
    <Wrapper>
      {structures.map((structure) => (
        <Structure structure={structure} />
      ))}
    </Wrapper>
  );
};

export default Structures;
