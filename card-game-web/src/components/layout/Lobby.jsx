import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchCurrentMatch,
  selectCurrentMatchId,
} from "../../store/lobby/lobby-store";
import Match from "../game/Match";
import QueueButton from "./QueueButton";

const Lobby = () => {
  const matchId = useSelector(selectCurrentMatchId);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!matchId) {
      dispatch(fetchCurrentMatch());
    }
  }, [dispatch, matchId]);

  return <>{matchId ? <Match matchId={matchId} /> : <QueueButton />}</>;
};

export default Lobby;
