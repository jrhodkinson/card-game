import React from "react";
import * as c from "../colors";
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

const Spacer = styled.div`
  background-color: ${c.darkGrey};
`;

const SubGrid = styled.div`
  background-color: ${c.darkGrey};
  display: grid;
  grid-template-columns: 200px 1fr 200px;
`;

const SecondaryPlayer = ({ player, active }) => (
  <>
    <Spacer />
    <SubGrid>
      <Deck size={player.deckSize} />
      <Hero
        entityId={player.entityId}
        name={player.user}
        health={player.health}
        active={active}
      />
      <Pile cards={player.discardPile} name="discard pile" />
    </SubGrid>
    <Spacer />
    <SpanAll>
      <SecondaryPlayerHand size={player.hand.length} />
    </SpanAll>
    <SpanAll>
      <Structures structures={player.structures} />
    </SpanAll>
  </>
);

export default SecondaryPlayer;
