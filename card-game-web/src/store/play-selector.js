import { PLAY_STATE } from "./play-reducer";

const getPlayState = (store) => store[PLAY_STATE];

export const getPendingCardInstanceId = (store) =>
  getPlayState(store).pendingCardInstanceId;

export const pendingCardRequiresTarget = (store) =>
  getPlayState(store).pendingCardInstanceId !== undefined;
