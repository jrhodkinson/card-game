import React from "react";
import { cardColor, darkCardColor, lightCardColor } from "../card/styles/color";
import { card } from "../card/styles/dimensions";
import styled from "styled-components";

const Wrapper = styled.div`
  border: ${card.BORDER_WIDTH}px solid #555;
  border-radius: ${card.BORDER_RADIUS}px;
  box-shadow: rgba(0, 0, 0, 0.2) 0 2px 1px -1px, rgba(0, 0, 0, 0.14) 0 1px 1px 0,
    rgba(0, 0, 0, 0.12) 0 1px 2px 0;
  margin: ${card.MARGIN_TOP_BOTTOM}px ${card.MARGIN_LEFT_RIGHT}px;
  padding: 6px;
  width: 180px;
  background-color: ${cardColor("CYAN")};
  display: grid;
  grid-template-columns: 1fr min-content;

  ${({ interactable }) => {
    if (interactable) {
      return `
          cursor: pointer;
          transition: transform 0.15s;
          
          &:hover {
            transform: scale(1.05) translate(0, -2.5%);
            box-shadow: ${cardColor("CYAN")} 0 0 8px 0;
          }
        `;
    }
  }};
`;

const Name = styled.div`
  background-color: ${lightCardColor("CYAN")};
  display: flex;
  align-items: center;
  padding: ${card.PADDING}px;
`;

const healthDiameter = 36;

const Health = styled.div`
  margin: auto 0 auto 6px;
  background-color: ${darkCardColor("RED")};
  color: white;
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
      <Name title={structure.flavor}>{structure.name}</Name>
      <Health>{structure.health}</Health>
      {structure.description}
    </Wrapper>
  );
};

export default Structure;
