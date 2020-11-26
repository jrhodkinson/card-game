import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchCurrentMatch,
  isQueueing,
  selectCurrentMatchId,
} from "../store/lobby/lobby-store";
import Match from "./Match";
import QueueButton from "./QueueButton";

const Lobby = () => {
  const matchId = useSelector(selectCurrentMatchId);
  const isQueuing = useSelector(isQueueing);
  const dispatch = useDispatch();

  useEffect(() => {
    if (isQueuing) {
      dispatch(fetchCurrentMatch());
    }
  }, [dispatch, isQueuing]);

  return <>{matchId ? <Match matchId={matchId} /> : <QueueButton />}</>;
};

export default Lobby;
