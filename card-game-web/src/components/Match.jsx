import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { login } from "../gateway/ws";
import { getActivePlayer, getInactivePlayer } from "../store/match-selector";
import CurrentTurn from "./CurrentTurn";
import MatchWebSocket from "./MatchWebSocket";
import PrimaryPlayer from "./player/PrimaryPlayer";
import SecondaryPlayer from "./player/SecondaryPlayer";
import Storefront from "./Storefront";

const Match = () => {
  const activePlayer = useSelector(getActivePlayer);
  const inactivePlayer = useSelector(getInactivePlayer);

  useEffect(() => {
    if (activePlayer.user) {
      login(activePlayer.user);
    }
  }, [activePlayer.user]);

  return (
    <>
      <MatchWebSocket />
      <SecondaryPlayer player={inactivePlayer} />
      <Storefront />
      <CurrentTurn />
      <PrimaryPlayer player={activePlayer} />
    </>
  );
};

export default Match;
