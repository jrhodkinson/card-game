import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { queue } from "../gateway/lobby";
import {
  fetchCurrentMatch,
  selectCurrentMatchId,
} from "../store/lobby/lobby-store";
import Match from "./Match";

const Lobby = () => {
  const matchId = useSelector(selectCurrentMatchId);
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchCurrentMatch());
  }, [dispatch]);

  return <>{matchId ? <Match /> : <button onClick={queue}>Queue</button>}</>;
};

export default Lobby;
