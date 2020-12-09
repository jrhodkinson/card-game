import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";
import useTooltip from "./useTooltip";

const uppercaseFirstLetter = (str) =>
  str.charAt(0).toUpperCase() + str.slice(1);

const Bold = styled.strong`
  font-weight: 500;
  color: ${c.accentTextOnWhite};
`;

const DescriptionPiece = ({ piece, first }) => {
  const text = first ? uppercaseFirstLetter(piece.token) : piece.token;

  if (!piece.context) {
    return text;
  }

  return <Bold>{text}</Bold>;
};

const DescriptionLine = ({ line }) => {
  return line
    .map((piece, index) => (
      <DescriptionPiece piece={piece} first={index === 0} key={index} />
    ))
    .reduce((prev, curr) => [prev, " ", curr]);
};

const Description = ({ description }) => {
  const { id, tooltip } = useTooltip();
  let piecesWithContext = description.flat().filter((piece) => piece.context);
  piecesWithContext = piecesWithContext.filter(
    (item, pos) =>
      piecesWithContext.findIndex((piece) => piece.context === item.context) ===
      pos
  );

  if (piecesWithContext.length > 0) {
    const tooltipText =
      piecesWithContext
        .map(
          (piece) => `${uppercaseFirstLetter(piece.token)}: ${piece.context}`
        )
        .join(".<br />") + ".";
    return (
      <>
        <span
          data-tip={tooltipText}
          data-for={id}
          data-multiline
          data-delay-show="750"
        >
          {description
            .map((line, index) => <DescriptionLine line={line} key={index} />)
            .reduce((prev, curr) => [prev, ". ", curr])}
          .
        </span>
        {tooltip}
      </>
    );
  }
  return (
    <>
      <span>
        {description
          .map((line, index) => <DescriptionLine line={line} key={index} />)
          .reduce((prev, curr) => [prev, ". ", curr])}
        .
      </span>
    </>
  );
};

export default Description;
