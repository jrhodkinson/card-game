import React, { useCallback, useEffect, useState } from "react";
import { connectToMatchStateWebSocket, MESSAGE_TYPES } from "../gateway/ws";

const MatchStateWebSocket = () => {
  const [latestMessage, setLatestMessage] = useState({});

  const handleWebSocketMessage = useCallback((messageEvent) => {
    const message = JSON.parse(messageEvent.data);
    if (message.type === MESSAGE_TYPES.MATCH_STATE) {
      setLatestMessage(message.payload);
    }
  }, []);

  useEffect(() => {
    const ws = connectToMatchStateWebSocket();
    ws.onmessage = handleWebSocketMessage;
    ws.onclose = () => setLatestMessage({});
    return ws.close;
  }, [handleWebSocketMessage]);

  return <pre>{JSON.stringify(latestMessage, null, 4)}</pre>;
};

export default MatchStateWebSocket;
