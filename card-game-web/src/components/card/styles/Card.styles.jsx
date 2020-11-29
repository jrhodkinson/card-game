import styled from "styled-components";
import { card, cost, image, header, description } from "./dimensions";

export const Card = styled.div`
  height: ${({ short }) =>
    short
      ? card.FULL_HEIGHT - description.HEIGHT - card.PADDING
      : card.FULL_HEIGHT}px;
  width: ${card.WIDTH}px;
  margin: ${card.MARGIN_TOP_BOTTOM}px ${card.MARGIN_LEFT_RIGHT}px;
  padding: ${card.PADDING}px;
  border-radius: ${card.BORDER_RADIUS}px;
  position: relative;

  flex-grow: 0;
  flex-shrink: 0;

  background-color: #e8e8e8;
  color: #333;
  box-shadow: rgba(0, 0, 0, 0.2) 0 2px 1px -1px, rgba(0, 0, 0, 0.14) 0 1px 1px 0,
    rgba(0, 0, 0, 0.12) 0 1px 2px 0;

  ${({ interactable, selected }) => {
    if (!interactable) {
      return;
    }

    let styles = `
        cursor: pointer;
        transition: transform 0.15s;
      `;

    if (selected) {
      styles += `
        transform: scale(1.02) translate(0, -2%);
        box-shadow: 0 0 0 3px #111, 0 0 8px 0 #FFF;
        
        &:hover {
          transform: scale(1.05) translate(0, -2.5%);
        }
      `;
    } else {
      styles += `
        &:hover {
          transform: scale(1.05) translate(0, -2.5%);
          box-shadow: 0 0 4px 0 #FFF;
        }
      `;
    }

    return styles;
  }};
`;

export const CardName = styled.div`
  background-color: #fff;
  height: ${header.HEIGHT}px;
  width: ${header.WIDTH}px;
  margin-bottom: ${header.MARGIN_BOTTOM}px;
  line-height: ${header.LINE_HEIGHT}px;
  padding: 0 ${card.PADDING}px;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-weight: 500;
`;

export const CardImage = styled.div`
  background-color: #e8e8e8;
  height: ${image.HEIGHT}px;
  width: ${image.WIDTH}px;
  margin-bottom: ${image.MARGIN_BOTTOM}px;
`;

export const CardDescription = styled.div`
  background-color: #fff;
  height: ${description.HEIGHT}px;
  width: ${description.WIDTH}px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
`;

export const ShortCardDescription = styled.div`
  background-color: #fff;

  position: absolute;
  top: ${header.HEIGHT + 2 * card.PADDING}px;
  bottom: ${card.PADDING}px;
  left: ${card.PADDING}px;
  right: ${card.PADDING}px;

  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;

  pointer-events: none;
`;

export const CardCost = styled.div`
  background-color: #e8e8e8;
  color: black;
  font-weight: bold;
  position: absolute;
  top: -${0.75 * cost.RADIUS}px;
  left: -${0.75 * cost.RADIUS}px;
  width: ${2 * cost.RADIUS}px;
  height: ${2 * cost.RADIUS}px;
  border-radius: ${cost.RADIUS}px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;

  &::after {
    position: absolute;
    content: "";
    border-radius: ${cost.RADIUS}px;
    border-bottom-color: transparent;
    -webkit-transform: rotate(-45deg);
    -moz-transform: rotate(-45deg);
    transform: rotate(-45deg);
    width: ${2 * cost.RADIUS}px;
    height: ${2 * cost.RADIUS}px;
  }
`;
