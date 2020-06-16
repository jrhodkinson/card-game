export const NAMESPACE = "match";

export const RECEIVED_MATCH_STATE = `${NAMESPACE}/RECEIVED_MATCH_STATE`;

export const receivedMatchState = (matchState) => ({
  type: RECEIVED_MATCH_STATE,
  matchState,
});
