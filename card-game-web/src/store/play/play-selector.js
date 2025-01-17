import { PLAY_STATE } from "./play-reducer";

const getPlayState = (store) => store[PLAY_STATE];

export const selectPendingCardEntityId = (store) =>
  getPlayState(store).pendingCardEntityId;

export const selectDoesPendingCardRequireDamageableTarget = (store) =>
  getPlayState(store).expecting === "damageable";

export const selectDoesPendingCardRequireStoreTarget = (store) =>
  getPlayState(store).expecting === "store";

export const selectDoesPendingCardRequireCardInHandTarget = (store) =>
  getPlayState(store).expecting === "hand";

export const selectDoesPendingCardRequireTarget = (store) =>
  getPlayState(store).expecting;
