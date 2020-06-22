import React from "react";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hero from "./Hero";

const SecondaryPlayer = ({ player }) => (
  <>
    <Hero name={player.username} health={player.health} />
    {player.hand.length} cards
    <Deck size={player.deckSize} />
    <Pile cards={player.discardPile} />
    <Structures structures={player.structures} />
  </>
);

export default SecondaryPlayer;
