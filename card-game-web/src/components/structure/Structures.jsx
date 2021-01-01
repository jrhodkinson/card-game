import React from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectedDamageableTarget } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireDamageableTarget } from "../../store/play/play-selector";
import Structure from "./Structure";

const Wrapper = styled.div`
  align-items: flex-start;
  display: flex;
  max-width: 100%;
`;

const Structures = ({ structures }) => {
  const dispatch = useDispatch();
  const structuresAreInteractable = useSelector(
    selectDoesPendingCardRequireDamageableTarget
  );
  const handleStructureClick = (structure) =>
    dispatch(selectedDamageableTarget(structure.entityId));
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
