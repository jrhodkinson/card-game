import React from "react";
import Cards from "../card/Cards";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hero from "./Hero";

const Player = ({ player }) => (
  <>
    <Hero name={player.username} health={player.health} />
    <Cards cards={player.hand} />
    <Deck size={player.deckSize} />
    <Pile cards={player.discardPile} />
    <Structures structures={player.structures} />
  </>
);

export default Player;
