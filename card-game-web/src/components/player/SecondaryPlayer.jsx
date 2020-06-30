import React from "react";
import styled from "styled-components";
import { card } from "../card/styles/dimensions";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hero from "./Hero";
import SecondaryPlayerHand from "./SecondaryPlayerHand";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: ${card.WIDTH * 1.5}px 1fr ${card.WIDTH * 1.5}px;
  grid-gap: 5px;
`;

const CenteredWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const DiscardPileWrapper = styled(CenteredWrapper)`
  grid-column: 1;
  grid-row: 1 / 3;
`;

const DeckWrapper = styled(CenteredWrapper)`
  grid-column: 3;
  grid-row: 1 / 3;
`;

const StructuresWrapper = styled(CenteredWrapper)`
  grid-column: span 3;
`;

const SecondaryPlayer = ({ player }) => (
  <Wrapper>
    <DiscardPileWrapper>
      <Pile cards={player.discardPile} />
    </DiscardPileWrapper>
    <Hero
      instanceId={player.instanceId}
      name={player.user}
      health={player.health}
    />
    <SecondaryPlayerHand size={player.hand.length} />
    <DeckWrapper>
      <Deck size={player.deckSize} />
    </DeckWrapper>
    <StructuresWrapper>
      <Structures structures={player.structures} />
    </StructuresWrapper>
  </Wrapper>
);

export default SecondaryPlayer;
