import { PLAY_STATE } from "./play-reducer";

const getPlayState = (store) => store[PLAY_STATE];

export const selectPendingCardEntityId = (store) =>
  getPlayState(store).pendingCardEntityId;

export const selectDoesPendingCardRequireTarget = (store) =>
  getPlayState(store).pendingCardEntityId !== undefined;
