import styled from "styled-components";
import * as c from "../../styles/colors";
import { card, cost, image, header, description } from "./dimensions";

export const Card = styled.div`
  height: ${({ short }) =>
    short
      ? card.FULL_HEIGHT - image.HEIGHT - card.PADDING
      : card.FULL_HEIGHT}px;
  width: ${card.WIDTH}px;
  margin: ${card.MARGIN_TOP_BOTTOM}px ${card.MARGIN_LEFT_RIGHT}px;
  padding: ${card.PADDING}px;
  border-radius: ${card.BORDER_RADIUS}px;
  position: relative;

  flex-grow: 0;
  flex-shrink: 0;

  background-color: ${c.offWhite};
  color: ${c.textOnWhite};
  box-shadow: ${c.darkestBlack} 0 3px 2px -2px, ${c.darkBlack} 0 2px 2px 0,
    ${c.darkGrey} 0 2px 3px 0;

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
        transform: scale(1.03) translate(0, -2%);
        box-shadow: 0 0 0 4px ${c.darkBlack}, 0 0 12px 0 ${c.white};
        
        &:hover {
          transform: scale(1.05) translate(0, -2.5%);
        }
      `;
    } else {
      styles += `
        &:hover {
          transform: scale(1.05) translate(0, -2.5%);
          box-shadow: 0 0 4px 0 ${c.white};
        }
      `;
    }

    return styles;
  }};
`;

export const CardName = styled.div`
  background-color: ${c.white};
  height: ${header.HEIGHT}px;
  width: ${header.WIDTH}px;
  margin-bottom: ${header.MARGIN_BOTTOM}px;
  line-height: ${header.LINE_HEIGHT}px;
  padding: 0 8px;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-weight: 500;
  font-size: ${header.FONT_SIZE}em;
`;

export const CardImage = styled.div`
  background-color: ${c.offWhite};
  height: ${image.HEIGHT}px;
  width: ${image.WIDTH}px;
  margin-bottom: ${image.MARGIN_BOTTOM}px;
`;

export const CardDescription = styled.div`
  background-color: ${c.white};
  height: ${description.HEIGHT}px;
  width: ${description.WIDTH}px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: ${description.FONT_SIZE}em;
`;

export const ShortCardDescription = styled.div`
  background-color: ${c.white};

  position: absolute;
  top: ${header.HEIGHT + 2 * card.PADDING}px;
  bottom: ${card.PADDING}px;
  left: ${card.PADDING}px;
  right: ${card.PADDING}px;

  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;

  cursor: default;

  font-size: ${description.FONT_SIZE}em;
`;

export const CardCost = styled.div`
  background-color: ${c.offWhite};
  color: ${c.textOnWhite};
  font-weight: bold;
  position: absolute;
  top: -${0.6 * cost.RADIUS}px;
  left: -${0.6 * cost.RADIUS}px;
  width: ${2 * cost.RADIUS}px;
  height: ${2 * cost.RADIUS}px;
  border-radius: ${cost.RADIUS}px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
`;

export const CardStump = styled(Card)`
  height: ${card.STUMP_HEIGHT}px;
  margin-top: 0;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
  background-color: ${c.mediumGrey};
`;
