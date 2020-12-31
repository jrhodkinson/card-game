import React from "react";
import { ORIENTATIONS } from "../card/CardStump";
import Deck from "../card/Deck";
import DiscardPile from "../card/DiscardPile";
import * as c from "../styles/colors";
import styled from "styled-components";
import * as S from "../../styles";
import Structures from "../structure/Structures";
import Hero from "./Hero";
import StumpHand from "./StumpHand";

const SpanAll = styled(S.Centered)`
  grid-column: span 3;
`;

const Spacer = styled.div`
  background-color: ${c.darkGrey};
  box-shadow: 0 4px 2px -2px ${c.darkBlack};
  z-index: 3;
`;

const SubGrid = styled.div`
  background-color: ${c.darkGrey};
  display: grid;
  grid-template-columns: 1fr 300px 1fr;
  box-shadow: 0 4px 2px -2px ${c.darkBlack};
  z-index: 3;
`;

const SecondaryPlayer = ({ player, active }) => (
  <>
    <Spacer />
    <SubGrid>
      <Deck size={player.deck.length} />
      <Hero
        entityId={player.entityId}
        name={player.user}
        health={player.health}
        active={active}
      />
      <DiscardPile cards={player.discardPile} />
    </SubGrid>
    <Spacer />
    <SpanAll>
      <StumpHand size={player.hand.length} orientation={ORIENTATIONS.BOTTOM} />
    </SpanAll>
    <SpanAll>
      <Structures structures={player.structures} />
    </SpanAll>
  </>
);

export default SecondaryPlayer;
