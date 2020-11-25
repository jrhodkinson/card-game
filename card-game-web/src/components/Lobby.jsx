import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { postJoinQueue } from "../gateway/lobby";
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

  return (
    <>
      {matchId ? (
        <Match matchId={matchId} />
      ) : (
        <button onClick={postJoinQueue}>Queue</button>
      )}
    </>
  );
};

export default Lobby;
