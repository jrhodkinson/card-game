import React from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import {
  isPrimaryPlayerActive,
  selectPrimaryPlayer,
  selectSecondaryPlayer,
  selectWinner,
} from "../../store/match/match-selector";
import CurrentTurn from "./CurrentTurn";
import MatchWebSocket from "./MatchWebSocket";
import PrimaryPlayer from "../player/PrimaryPlayer";
import SecondaryPlayer from "../player/SecondaryPlayer";
import QueueButton from "./QueueButton";
import Storefront from "./Storefront";

const Wrapper = styled.main`
  display: flex;
  flex-direction: column;
`;

const Match = ({ matchId }) => {
  const winner = useSelector(selectWinner);
  const primaryPlayer = useSelector(selectPrimaryPlayer);
  const secondaryPlayer = useSelector(selectSecondaryPlayer);
  const primaryPlayerActive = useSelector(isPrimaryPlayerActive);

  return (
    <>
      <MatchWebSocket matchId={matchId} />
      {winner ? (
        <>
          <div>Match is over, winner: {winner}</div>
          <QueueButton />
        </>
      ) : (
        <Wrapper>
          <SecondaryPlayer
            player={secondaryPlayer}
            active={!primaryPlayerActive}
          />
          <Storefront />
          <CurrentTurn />
          <PrimaryPlayer player={primaryPlayer} active={primaryPlayerActive} />
        </Wrapper>
      )}
    </>
  );
};

export default Match;
