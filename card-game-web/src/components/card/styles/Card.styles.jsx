import { darken } from "polished";
import styled from "styled-components";
import { cardColor, lightCardColor, darkCardColor } from "./color";
import { card, cost, image, header, description } from "./dimensions";

export const Card = styled.div`
  height: ${({ hideDescription }) =>
    hideDescription
      ? card.FULL_HEIGHT - description.HEIGHT - card.PADDING
      : card.FULL_HEIGHT}px;
  width: ${card.WIDTH}px;
  margin: ${card.MARGIN}px;
  padding: ${card.PADDING}px;
  border: ${card.BORDER_WIDTH}px solid #555;
  border-radius: ${card.BORDER_RADIUS}px;
  position: relative;

  flex-grow: 0;
  flex-shrink: 0;

  background-color: ${({ background }) => cardColor(background)};
  box-shadow: rgba(0, 0, 0, 0.2) 0 2px 1px -1px, rgba(0, 0, 0, 0.14) 0 1px 1px 0,
    rgba(0, 0, 0, 0.12) 0 1px 2px 0;

  ${({ interactable, background }) => {
    if (interactable) {
      return `&:hover { box-shadow: ${cardColor(
        background
      )} 0 0 8px 0; cursor: pointer; }`;
    }
  }};

  ${({ selected, background }) =>
    selected && `box-shadow: ${darken(0.4, cardColor(background))} 0 0 8px 0`};
`;

export const CardName = styled.div`
  background-color: ${({ background }) => lightCardColor(background)};
  height: ${header.HEIGHT}px;
  width: ${header.WIDTH}px;
  margin-bottom: ${header.MARGIN_BOTTOM}px;
  line-height: ${header.LINE_HEIGHT}px;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
`;

export const CardImage = styled.div`
  background-color: ${({ background }) => darkCardColor(background)};
  height: ${image.HEIGHT}px;
  width: ${image.WIDTH}px;
  margin-bottom: ${image.MARGIN_BOTTOM}px;
`;

export const CardDescription = styled.div`
  background-color: ${({ background }) => lightCardColor(background)};
  height: ${description.HEIGHT}px;
  width: ${description.WIDTH}px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
`;

export const CardCost = styled.div`
  background-color: ${({ background }) => darkCardColor(background)};
  color: black;
  font-weight: bold;
  position: absolute;
  top: -${cost.RADIUS}px;
  left: -${cost.RADIUS}px;
  width: ${2 * cost.RADIUS}px;
  height: ${2 * cost.RADIUS}px;
  border-radius: ${cost.RADIUS}px;
  display: flex;
  align-items: center;
  justify-content: center;

  &::after {
    position: absolute;
    content: "";
    border: ${card.BORDER_WIDTH}px solid #555;
    border-radius: ${cost.RADIUS}px;
    border-bottom-color: transparent;
    -webkit-transform: rotate(-45deg);
    -moz-transform: rotate(-45deg);
    transform: rotate(-45deg);
    width: ${2 * cost.RADIUS}px;
    height: ${2 * cost.RADIUS}px;
  }
`;
