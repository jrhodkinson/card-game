import React from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import {
  isPrimaryPlayerActive,
  matchStateHasInitialised,
  selectPrimaryPlayer,
  selectSecondaryPlayer,
  selectWinner,
} from "../../store/match/match-selector";
import FadeIn from "../common/FadeIn";
import PrimaryPlayer from "../player/PrimaryPlayer";
import SecondaryPlayer from "../player/SecondaryPlayer";
import CurrentTurn from "./CurrentTurn";
import QueueButton from "./QueueButton";
import Storefront from "./Storefront";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr 1100px 1fr;
  grid-template-rows: min-content min-content min-content 1fr min-content 1fr min-content min-content;
  height: 100%;
  width: 100%;
`;

const Spacer = styled.div``;

const Match = () => {
  const hasInitialised = useSelector(matchStateHasInitialised);
  const winner = useSelector(selectWinner);
  const primaryPlayer = useSelector(selectPrimaryPlayer);
  const secondaryPlayer = useSelector(selectSecondaryPlayer);
  const primaryPlayerActive = useSelector(isPrimaryPlayerActive);

  if (!hasInitialised) {
    return null;
  }

  if (winner) {
    return (
      <>
        <div>Match is over, winner: {winner}</div>
        <QueueButton />
      </>
    );
  }

  return (
    <FadeIn fast>
      <Wrapper>
        <SecondaryPlayer
          player={secondaryPlayer}
          active={!primaryPlayerActive}
        />
        {primaryPlayerActive ? (
          <Spacer />
        ) : (
          <CurrentTurn active={primaryPlayerActive} />
        )}
        <Storefront active={primaryPlayerActive} />
        {primaryPlayerActive ? (
          <CurrentTurn active={primaryPlayerActive} />
        ) : (
          <Spacer />
        )}
        <PrimaryPlayer player={primaryPlayer} active={primaryPlayerActive} />
      </Wrapper>
    </FadeIn>
  );
};

export default Match;
