import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { connectToMatchWebSocket } from "../gateway/ws";

const MatchWebSocket = ({ matchId }) => {
  const dispatch = useDispatch();
  useEffect(() => connectToMatchWebSocket(dispatch, matchId), [
    dispatch,
    matchId,
  ]);
  return null;
};

export default MatchWebSocket;
