import React from "react";
import { darken } from "polished";
import styled from "styled-components";

const Wrapper = styled.div`
  border: 1px solid black;
  box-shadow: rgba(0, 0, 0, 0.2) 0 2px 1px -1px, rgba(0, 0, 0, 0.14) 0 1px 1px 0,
    rgba(0, 0, 0, 0.12) 0 1px 2px 0;
  margin: 10px;
  padding: 10px;
  min-width: 180px;
  width: 180px;
  background-color: #cff;
  display: flex;
  flex-direction: column;
  align-items: center;

  ${({ interactable }) => {
    if (interactable) {
      return `&:hover { box-shadow: ${darken(
        0.4,
        "#cff"
      )} 0 0 8px 0; cursor: pointer; }`;
    }
  }};
`;

const Name = styled.div`
  font-weight: bold;
`;

const healthDiameter = 36;

const Health = styled.div`
  background-color: #f33;
  color: white;
  margin-top: 10px;
  text-align: center;
  line-height: ${healthDiameter}px;
  width: ${healthDiameter}px;
  height: ${healthDiameter}px;
  border-radius: ${healthDiameter / 2}px;
`;

const Structure = ({
  structure,
  interactable = false,
  onStructureClick = () => {},
}) => {
  return (
    <Wrapper
      interactable={interactable}
      onClick={() => onStructureClick(structure)}
    >
      <Name>{structure.name}</Name>
      <Health>{structure.health}</Health>
    </Wrapper>
  );
};

export default Structure;
