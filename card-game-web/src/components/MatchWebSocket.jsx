import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { connectToMatchWebSocket } from "../gateway/ws";

const MatchWebSocket = () => {
  const dispatch = useDispatch();
  useEffect(() => connectToMatchWebSocket(dispatch), [dispatch]);
  return null;
};

export default MatchWebSocket;
