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
import Loading from "../common/Loading";
import ActiveMatchList from "../lobby/ActiveMatchList";
import PrimaryPlayer from "../player/PrimaryPlayer";
import SecondaryPlayer from "../player/SecondaryPlayer";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";
import CurrentTurn from "./CurrentTurn";
import QueueButton from "../lobby/QueueButton";
import Storefront from "./Storefront";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr ${MAIN_COLUMN_WIDTH} 1fr;
  grid-template-rows: min-content min-content min-content 1fr min-content 1fr min-content min-content;
  height: 100%;
  width: 100%;
`;

const MatchOver = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  padding: 30px 10px 0;
  display: flex;
  height: 100%;
  align-items: center;
  flex-direction: column;
`;

const Winner = styled.div`
  margin-bottom: 20px;
`;

const Spacer = styled.div``;

const Match = () => {
  const hasInitialised = useSelector(matchStateHasInitialised);
  const winner = useSelector(selectWinner);
  const primaryPlayer = useSelector(selectPrimaryPlayer);
  const secondaryPlayer = useSelector(selectSecondaryPlayer);
  const primaryPlayerActive = useSelector(isPrimaryPlayerActive);

  if (!hasInitialised) {
    return <Loading />;
  }

  if (winner) {
    return (
      <MatchOver>
        <Winner>Match is over, winner: {winner}</Winner>
        <QueueButton />
        <ActiveMatchList />
      </MatchOver>
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
