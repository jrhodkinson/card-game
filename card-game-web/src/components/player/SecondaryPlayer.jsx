import React from "react";
import styled from "styled-components";
import * as S from "../../styles";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hero from "./Hero";
import SecondaryPlayerHand from "./SecondaryPlayerHand";

const SpanAll = styled(S.Centered)`
  grid-column: span 3;
`;

const SecondaryPlayer = ({ player, active }) => (
  <>
    <Pile cards={player.discardPile} />
    <SecondaryPlayerHand size={player.hand.length} />
    <Deck size={player.deckSize} />
    <SpanAll>
      <Hero
        entityId={player.entityId}
        name={player.user}
        health={player.health}
        active={active}
      />
    </SpanAll>
    <SpanAll>
      <Structures structures={player.structures} />
    </SpanAll>
  </>
);

export default SecondaryPlayer;
