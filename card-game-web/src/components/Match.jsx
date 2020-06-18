import React from "react";
import { useSelector } from "react-redux";
import { getActivePlayer, getInactivePlayer } from "../store/match-selector";
import CurrentTurn from "./CurrentTurn";
import MatchWebSocket from "./MatchWebSocket";
import Player from "./player/Player";
import Storefront from "./Storefront";

const Match = () => {
  const activePlayer = useSelector(getActivePlayer);
  const inactivePlayer = useSelector(getInactivePlayer);
  return (
    <>
      <MatchWebSocket />
      <Player player={inactivePlayer} />
      <Storefront />
      <CurrentTurn />
      <Player player={activePlayer} />
    </>
  );
};

export default Match;
