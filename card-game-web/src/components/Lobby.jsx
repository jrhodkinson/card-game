import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { postJoinQueue } from "../gateway/lobby";
import { login, selectUser } from "../store/account/account-store";
import {
  fetchCurrentMatch,
  selectCurrentMatchId,
} from "../store/lobby/lobby-store";
import Match from "./Match";

const Lobby = () => {
  const matchId = useSelector(selectCurrentMatchId);
  const user = useSelector(selectUser);
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchCurrentMatch());
  }, [dispatch]);

  const handleUsernameKeyUp = (e) => {
    if (e.key === "Enter") {
      dispatch(login(e.currentTarget.value));
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
          <button onClick={postJoinQueue}>Queue</button>
        </>
      )}
      <div>Logged in as {user}</div>
    </>
  );
};

export default Lobby;
