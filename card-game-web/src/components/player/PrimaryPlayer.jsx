import React from "react";
import styled from "styled-components";
import * as S from "../../styles";
import { card } from "../card/styles/dimensions";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hand from "./Hand";
import Hero from "./Hero";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: ${card.WIDTH * 1.5}px 1fr ${card.WIDTH * 1.5}px;
  grid-gap: 5px;
  overflow: hidden;
  width: 100%;
`;

const SpanAll = styled(S.Centered)`
  grid-column: span 3;
`;

const PrimaryPlayer = ({ player }) => (
  <Wrapper>
    <SpanAll>
      <Structures structures={player.structures} />
    </SpanAll>
    <SpanAll>
      <Hero
        instanceId={player.instanceId}
        name={player.user}
        health={player.health}
      />
    </SpanAll>
    <Deck size={player.deckSize} />
    <Hand hand={player.hand} />
    <Pile cards={player.discardPile} />
  </Wrapper>
);

export default PrimaryPlayer;
