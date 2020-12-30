import React from "react";
import { useSelector } from "react-redux";
import { selectIsSpectating } from "../../store/lobby/lobby-store";
import { ORIENTATIONS } from "../card/CardStump";
import * as c from "../styles/colors";
import styled from "styled-components";
import * as S from "../../styles";
import Deck from "../card/Deck";
import Pile from "../card/Pile";
import Structures from "../structure/Structures";
import Hand from "./Hand";
import Hero from "./Hero";
import StumpHand from "./StumpHand";

const SpanAll = styled(S.Centered)`
  grid-column: span 3;
`;

const Spacer = styled.div`
  background-color: ${c.darkGrey};
  box-shadow: 0 -4px 2px -2px ${c.darkBlack};
  z-index: 3;
`;

const SubGrid = styled.div`
  background-color: ${c.darkGrey};
  display: grid;
  grid-template-columns: 1fr 300px 1fr;
  box-shadow: 0 -4px 2px -2px ${c.darkBlack};
  z-index: 3;
`;

const PrimaryPlayer = ({ player, active }) => {
  const isSpectating = useSelector(selectIsSpectating);
  return (
    <>
      <SpanAll>
        <Structures structures={player.structures} />
      </SpanAll>
      <SpanAll>
        {isSpectating ? (
          <StumpHand size={player.hand.length} orientation={ORIENTATIONS.TOP} />
        ) : (
          <Hand hand={player.hand} interactable={active} />
        )}
      </SpanAll>
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
    </>
  );
};

export default PrimaryPlayer;
