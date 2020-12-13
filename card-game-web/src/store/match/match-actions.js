export const NAMESPACE = "match";

export const MATCH_ENDED = `${NAMESPACE}/MATCH_ENDED`;
export const RECEIVED_MATCH_STATE = `${NAMESPACE}/RECEIVED_MATCH_STATE`;
export const TURN_ENDED = `${NAMESPACE}/TURN_ENDED`;
export const TURN_WILL_END_AT = `${NAMESPACE}/TURN_WILL_END_AT`;

export const receivedMatchState = (matchState) => ({
  type: RECEIVED_MATCH_STATE,
  matchState,
});

export const matchEnded = (winner) => ({
  type: MATCH_ENDED,
  winner,
});

export const turnEnded = (previousUser) => ({
  type: TURN_ENDED,
  previousUser,
});

export const turnWillEndAt = (time) => ({
  type: TURN_WILL_END_AT,
  time,
});
