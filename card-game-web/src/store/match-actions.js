export const NAMESPACE = "match";

export const MATCH_ENDED = `${NAMESPACE}/MATCH_ENDED`;
export const RECEIVED_MATCH_STATE = `${NAMESPACE}/RECEIVED_MATCH_STATE`;

export const receivedMatchState = (matchState) => ({
  type: RECEIVED_MATCH_STATE,
  matchState,
});

export const matchEnded = (winner) => ({
  type: MATCH_ENDED,
  winner,
});
