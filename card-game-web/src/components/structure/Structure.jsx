import React from "react";
import * as c from "../styles/colors";
import { card, header } from "../card/styles/dimensions";
import styled from "styled-components";
import Health from "../common/Health";
import useTooltip from "../common/useTooltip";

const Wrapper = styled.div`
  border-radius: ${card.BORDER_RADIUS}px;
  box-shadow: ${c.darkestBlack} 0 2px 1px -1px, ${c.darkBlack} 0 1px 1px 0,
    ${c.darkGrey} 0 1px 2px 0;
  margin: ${card.MARGIN_TOP_BOTTOM}px ${card.MARGIN_LEFT_RIGHT}px;
  padding: 6px;
  width: 180px;
  background-color: ${c.offWhite};
  color: ${c.textOnWhite};
  display: grid;
  grid-template-columns: 1fr min-content;

  ${({ interactable }) => {
    if (interactable) {
      return `
          cursor: pointer;
          transition: transform 0.15s;
          
          &:hover {
            transform: scale(1.05) translate(0, -2.5%);
            box-shadow: ${c.white} 0 0 8px 0;
          }
        `;
    }
  }};
`;

const Name = styled.div`
  background-color: ${c.white};
  display: flex;
  align-items: center;
  justify-content: center;
  padding: ${card.PADDING}px;
  font-weight: 500;
  font-size: ${header.FONT_SIZE}em;
  margin-right: 6px;
`;

const Structure = ({
  structure,
  interactable = false,
  onStructureClick = () => {},
}) => {
  const { id, tooltip } = useTooltip();
  return (
    <Wrapper
      interactable={interactable}
      onClick={() => onStructureClick(structure)}
      data-tip={structure.description}
      data-for={id}
    >
      <Name title={structure.flavor}>{structure.name}</Name>
      <Health health={structure.health} />
      {tooltip}
    </Wrapper>
  );
};

export default Structure;
