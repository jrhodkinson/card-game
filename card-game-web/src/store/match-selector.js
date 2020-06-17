import { MATCH_STATE } from "./match-reducer";

const getMatchState = (store) => store[MATCH_STATE];

export const getActivePlayer = (store) => getMatchState(store).activePlayer;
export const getInactivePlayer = (store) => getMatchState(store).inactivePlayer;

export const getCurrentTurn = (store) => getMatchState(store).currentTurn;
export const getStorefront = (store) => getMatchState(store).storefront;
