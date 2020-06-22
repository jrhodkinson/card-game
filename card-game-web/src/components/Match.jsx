import React from "react";
import { useSelector } from "react-redux";
import { getActivePlayer, getInactivePlayer } from "../store/match-selector";
import CurrentTurn from "./CurrentTurn";
import MatchWebSocket from "./MatchWebSocket";
import PrimaryPlayer from "./player/PrimaryPlayer";
import SecondaryPlayer from "./player/SecondaryPlayer";
import Storefront from "./Storefront";

const Match = () => {
  const activePlayer = useSelector(getActivePlayer);
  const inactivePlayer = useSelector(getInactivePlayer);
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
