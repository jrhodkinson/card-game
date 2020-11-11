import { MATCH_STATE } from "./match-reducer";

const selectMatchState = (store) => store[MATCH_STATE];

export const selectActivePlayer = (store) =>
  selectMatchState(store).activePlayer;
export const selectInactivePlayer = (store) =>
  selectMatchState(store).inactivePlayer;

export const selectCurrentTurn = (store) => selectMatchState(store).currentTurn;
export const selectStorefront = (store) => selectMatchState(store).storefront;

export const selectWinner = (store) => selectMatchState(store).winner;
