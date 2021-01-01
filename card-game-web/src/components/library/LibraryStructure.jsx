import React from "react";
import Description from "../common/Description";
import { Element } from "./Library";

const LibraryStructure = ({ structure }) => (
  <>
    <Element strong>{structure.name}</Element>
    <Element>{structure.health}</Element>
    <Element>
      <Description lines={structure.description.lines.slice(1)} />
    </Element>
  </>
);

export default LibraryStructure;
