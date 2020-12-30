import React from "react";
import Pile from "./Pile";

const Deck = ({ cards }) => (
  <Pile cards={cards} name="deck" tooltipHeader="(shuffled)" />
);

export default Deck;
