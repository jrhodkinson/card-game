import React, { useEffect } from "react";
import ReactTooltip from "react-tooltip";
import styled from "styled-components";
import * as c from "../styles/colors";
import useTooltip from "./useTooltip";

const uppercaseFirstLetter = (str) =>
  str.charAt(0).toUpperCase() + str.slice(1);

const Bold = styled.span`
  font-weight: 500;
  color: ${c.accentTextOnWhite};
`;

const DescriptionPiece = ({ piece, first }) => {
  const { id, tooltip } = useTooltip();
  const text = first ? uppercaseFirstLetter(piece.token) : piece.token;

  if (!piece.context) {
    return text;
  }

  return (
    <>
      <Bold data-tip={piece.context} data-for={id}>
        {text}
        {"\u00A0"}
      </Bold>
      {tooltip}
    </>
  );
};

const DescriptionLine = ({ line }) => {
  return line
    .map((piece, index) => (
      <DescriptionPiece piece={piece} first={index === 0} key={index} />
    ))
    .reduce((prev, curr) => [prev, " ", curr]);
};

const Description = ({ description }) => {
  return (
    <>
      {description
        .map((line, index) => <DescriptionLine line={line} key={index} />)
        .reduce((prev, curr) => [prev, ". ", curr])}
      .
    </>
  );
};

export default Description;
