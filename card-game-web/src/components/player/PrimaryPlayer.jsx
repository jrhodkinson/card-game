import React from "react";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hand from "./Hand";
import Hero from "./Hero";

const PrimaryPlayer = ({ player }) => (
  <>
    <Hero name={player.username} health={player.health} />
    <Hand hand={player.hand} />
    <Deck size={player.deckSize} />
    <Pile cards={player.discardPile} />
    <Structures structures={player.structures} />
  </>
);

export default PrimaryPlayer;
