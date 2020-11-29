import React from "react";
import styled from "styled-components";
import * as S from "../../styles";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hand from "./Hand";
import Hero from "./Hero";

const SpanAll = styled(S.Centered)`
  grid-column: span 3;
`;

const PrimaryPlayer = ({ player, active }) => (
  <>
    <SpanAll>
      <Structures structures={player.structures} />
    </SpanAll>
    <SpanAll>
      <Hero
        entityId={player.entityId}
        name={player.user}
        health={player.health}
        active={active}
      />
    </SpanAll>
    <Deck size={player.deckSize} />
    <Hand hand={player.hand} interactable={active} />
    <Pile cards={player.discardPile} />
  </>
);

export default PrimaryPlayer;
