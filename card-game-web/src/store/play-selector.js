import { PLAY_STATE } from "./play-reducer";

const getPlayState = (store) => store[PLAY_STATE];

export const getPendingCardEntityId = (store) =>
  getPlayState(store).pendingCardEntityId;

export const pendingCardRequiresTarget = (store) =>
  getPlayState(store).pendingCardEntityId !== undefined;
