import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  selectAccountId,
  selectHaveInitialisedAccountId,
} from "../../store/account/account-store";
import {
  fetchQueueStatus,
  selectHaveInitialisedMatchId,
  selectCurrentMatchId,
  selectIsGameOffline,
} from "../../store/lobby/lobby-store";
import Register from "../account/Register";
import Loading from "../common/Loading";
import MatchWrapper from "../game/MatchWrapper";
import GameOffline from "./GameOffline";
import Lobby from "./Lobby";

const Main = () => {
  const accountId = useSelector(selectAccountId);
  const haveInitialisedAccountId = useSelector(selectHaveInitialisedAccountId);
  const matchId = useSelector(selectCurrentMatchId);
  const haveInitialisedMatchId = useSelector(selectHaveInitialisedMatchId);
  const isGameOffline = useSelector(selectIsGameOffline);
  const dispatch = useDispatch();

  useEffect(() => {
    if (accountId && (!matchId || !haveInitialisedMatchId)) {
      dispatch(fetchQueueStatus());
    }
  }, [accountId, dispatch, haveInitialisedMatchId, matchId]);

  if (!haveInitialisedAccountId) {
    return <Loading />;
  }

  if (!accountId) {
    return <Register />;
  }

  if (matchId) {
    return <MatchWrapper />;
  }

  if (isGameOffline) {
    return <GameOffline />;
  }

  if (haveInitialisedMatchId) {
    return <Lobby />;
  }

  return <Loading />;
};

export default Main;
