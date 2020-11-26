import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { connectToMatchWebSocket } from "../gateway/ws";
import { selectAccountId } from "../store/account/account-store";

const MatchWebSocket = ({ matchId }) => {
  const accountId = useSelector(selectAccountId);
  const dispatch = useDispatch();
  useEffect(() => connectToMatchWebSocket(dispatch, matchId), [
    dispatch,
    matchId,
    accountId,
  ]);
  return null;
};

export default MatchWebSocket;
