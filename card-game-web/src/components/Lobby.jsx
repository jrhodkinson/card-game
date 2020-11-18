import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { login } from "../gateway/account";
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

  const handleUsernameKeyUp = (e) => {
    if (e.key === "Enter") {
      login(e.currentTarget.value);
    }
  };

  return (
    <>
      {matchId ? (
        <Match matchId={matchId} />
      ) : (
        <>
          <input
            type="text"
            placeholder="Username"
            onKeyUp={handleUsernameKeyUp}
          />
          <button onClick={queue}>Queue</button>
        </>
      )}
    </>
  );
};

export default Lobby;
