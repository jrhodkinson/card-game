export const NAMESPACE = "socket";

export const RECEIVED_PING = `${NAMESPACE}/RECEIVED_PING`;

export const receivedPing = (timestamp) => ({
  type: RECEIVED_PING,
  timestamp,
});
