import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  selectAccountId,
  selectHaveInitialisedAccountId,
} from "../../store/account/account-store";
import {
  continueFetchingQueueStatusUntilReceivedMatchIdOrNotInQueue,
  selectHaveInitialisedMatchId,
  selectCurrentMatchId,
} from "../../store/lobby/lobby-store";
import Register from "../account/Register";
import Loading from "../common/Loading";
import MatchWrapper from "../game/MatchWrapper";
import Lobby from "./Lobby";

const GameMain = () => {
  const accountId = useSelector(selectAccountId);
  const haveInitialisedAccountId = useSelector(selectHaveInitialisedAccountId);
  const matchId = useSelector(selectCurrentMatchId);
  const haveInitialisedMatchId = useSelector(selectHaveInitialisedMatchId);
  const dispatch = useDispatch();

  useEffect(() => {
    if (accountId && (!matchId || !haveInitialisedMatchId)) {
      dispatch(continueFetchingQueueStatusUntilReceivedMatchIdOrNotInQueue());
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

  if (haveInitialisedMatchId) {
    return <Lobby />;
  }

  return <Loading />;
};

export default GameMain;
