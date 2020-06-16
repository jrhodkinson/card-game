import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { connectToMatchStateWebSocket, toAction } from "../gateway/ws";

const MatchWebSocket = () => {
  const dispatch = useDispatch();

  useEffect(() => {
    const ws = connectToMatchStateWebSocket();
    ws.onmessage = (messageEvent) => {
      const action = toAction(messageEvent);
      if (action) {
        dispatch(action);
      }
    };
    return ws.close;
  }, [dispatch]);

  return null;
};

export default MatchWebSocket;
