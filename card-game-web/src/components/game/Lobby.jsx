import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchCurrentMatch,
  selectCurrentMatchId,
} from "../../store/lobby/lobby-store";
import Match from "./Match";
import MatchWebSocket from "./MatchWebSocket";
import QueueButton from "./QueueButton";

const Lobby = () => {
  const matchId = useSelector(selectCurrentMatchId);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!matchId) {
      dispatch(fetchCurrentMatch());
    }
  }, [dispatch, matchId]);

  if (matchId) {
    return (
      <>
        <MatchWebSocket matchId={matchId} />
        <Match matchId={matchId} />
      </>
    );
  }

  return <QueueButton />;
};

export default Lobby;
