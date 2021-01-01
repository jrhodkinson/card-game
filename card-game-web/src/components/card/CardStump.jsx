import React from "react";
import * as S from "./styles/Card.styles";

export const ORIENTATIONS = {
  TOP: "top",
  BOTTOM: "bottom",
};

const CardStump = ({ orientation }) => (
  <S.CardStump orientation={orientation} />
);

export default CardStump;
