import React from "react";
import { endTurn } from "../../gateway/ws";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hand from "./Hand";
import Hero from "./Hero";

const PrimaryPlayer = ({ player }) => (
  <>
    <Hero
      instanceId={player.instanceId}
      name={player.user}
      health={player.health}
    />
    <Hand hand={player.hand} />
    <Deck size={player.deckSize} />
    <Pile cards={player.discardPile} />
    <Structures structures={player.structures} />
    <button onClick={endTurn}>End turn</button>
  </>
);

export default PrimaryPlayer;
