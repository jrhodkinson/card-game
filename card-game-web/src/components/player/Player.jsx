import React from "react";
import Cards from "../card/Cards";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Hero from "./Hero";

const Player = ({ player }) => (
  <>
    <pre>{JSON.stringify(player, 0, 4)}</pre>
    <Hero name={player.username} health={player.health} />
    <Cards cards={player.hand} />
    <Deck size={player.deckSize} />
    <Pile cards={player.discardPile} />
  </>
);

export default Player;
