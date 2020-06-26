import React from "react";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play-actions";
import Structure from "./Structure";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  overflow: auto;
  max-width: 100%;
`;

const Structures = ({ structures }) => {
  const dispatch = useDispatch();
  const handleStructureClick = (structure) =>
    dispatch(selectedTarget(structure.instanceId));
  return (
    <Wrapper>
      {structures.map((structure) => (
        <Structure
          key={structure.instanceId}
          structure={structure}
          onStructureClick={handleStructureClick}
        />
      ))}
    </Wrapper>
  );
};

export default Structures;
