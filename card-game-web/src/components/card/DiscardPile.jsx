import React from "react";
import Pile from "./Pile";

const DiscardPile = ({ cards }) => (
  <Pile cards={cards} name="discard pile" align="right" />
);

export default DiscardPile;
