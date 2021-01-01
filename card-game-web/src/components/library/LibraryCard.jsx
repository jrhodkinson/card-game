import React from "react";
import Description from "../common/Description";
import { Element } from "./Library";

const LibraryCard = ({ card }) => (
  <>
    <Element strong>{card.name}</Element>
    <Element>{card.cost}M</Element>
    <Element>
      <Description lines={card.description.lines} />
    </Element>
  </>
);

export default LibraryCard;
