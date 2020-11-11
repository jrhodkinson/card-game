import React from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireTarget } from "../../store/play/play-selector";
import Structure from "./Structure";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  max-width: 100%;
`;

const Structures = ({ structures }) => {
  const dispatch = useDispatch();
  const structuresAreInteractable = useSelector(
    selectDoesPendingCardRequireTarget
  );
  const handleStructureClick = (structure) =>
    dispatch(selectedTarget(structure.entityId));
  return (
    <Wrapper>
      {structures.map((structure) => (
        <Structure
          key={structure.entityId}
          structure={structure}
          interactable={structuresAreInteractable}
          onStructureClick={handleStructureClick}
        />
      ))}
    </Wrapper>
  );
};

export default Structures;
