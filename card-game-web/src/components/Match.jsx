import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import { login } from "../gateway/ws";
import {
  selectActivePlayer,
  selectInactivePlayer,
  selectWinner,
} from "../store/match/match-selector";
import CurrentTurn from "./CurrentTurn";
import MatchWebSocket from "./MatchWebSocket";
import PrimaryPlayer from "./player/PrimaryPlayer";
import SecondaryPlayer from "./player/SecondaryPlayer";
import Storefront from "./Storefront";

const Wrapper = styled.main`
  display: flex;
  flex-direction: column;
`;

const Match = ({ matchId }) => {
  const winner = useSelector(selectWinner);
  const activePlayer = useSelector(selectActivePlayer);
  const inactivePlayer = useSelector(selectInactivePlayer);

  useEffect(() => {
    if (activePlayer.user) {
      login(activePlayer.user);
    }
  }, [activePlayer.user]);

  return (
    <Wrapper>
      <MatchWebSocket matchId={matchId} />
      {winner ? (
        "Match is over, winner: " + winner
      ) : (
        <>
          <SecondaryPlayer player={inactivePlayer} />
          <Storefront />
          <CurrentTurn />
          <PrimaryPlayer player={activePlayer} />
        </>
      )}
    </Wrapper>
  );
};

export default Match;
