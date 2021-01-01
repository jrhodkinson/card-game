import React from "react";
import { useSelector } from "react-redux";
import { selectCurrentMatchId } from "../../store/lobby/lobby-store";
import Match from "../game/Match";
import MatchWebSocket from "../game/MatchWebSocket";
import SelectItemTooltip from "./SelectItemTooltip";

const MatchWrapper = () => {
  const matchId = useSelector(selectCurrentMatchId);
  return (
    <>
      <MatchWebSocket matchId={matchId} />
      <Match matchId={matchId} />
      <SelectItemTooltip />
    </>
  );
};

export default MatchWrapper;
