import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchCurrentMatch,
  haveInitialisedMatchId,
  selectCurrentMatchId,
} from "../../store/lobby/lobby-store";
import Loading from "../common/Loading";
import MatchWrapper from "../game/MatchWrapper";
import Lobby from "./Lobby";

const Main = () => {
  const matchId = useSelector(selectCurrentMatchId);
  const haveInitialised = useSelector(haveInitialisedMatchId);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!matchId) {
      dispatch(fetchCurrentMatch());
    }
  }, [dispatch, matchId]);

  if (matchId) {
    return <MatchWrapper />;
  }

  if (haveInitialised) {
    return <Lobby />;
  }

  return <Loading />;
};

export default Main;
