import { selectUser } from "../account/account-store";
import { selectIsSpectating } from "../lobby/lobby-store";
import { MATCH_STATE } from "./match-reducer";

const selectMatchState = (store) => store[MATCH_STATE].state;

const selectPrimaryUser = (store) => {
  if (selectIsSpectating(store)) {
    return Object.keys(selectMatchState(store).players).sort((a, b) =>
      a.localeCompare(b)
    )[0];
  } else {
    selectUser(store);
  }
};
const selectSecondaryUser = (store) => {
  const users = Object.keys(selectMatchState(store).players);
  const primaryUser = selectPrimaryUser(store);
  return users.filter((users) => users !== primaryUser)[0];
};

export const matchStateHasInitialised = (store) =>
  store[MATCH_STATE].initialised;

export const selectPrimaryPlayer = (store) =>
  selectMatchState(store).players[selectPrimaryUser(store)];
export const selectSecondaryPlayer = (store) =>
  selectMatchState(store).players[selectSecondaryUser(store)];

export const isPrimaryPlayerActive = (store) =>
  selectPrimaryUser(store) === selectMatchState(store).activeUser;

export const selectCurrentTurn = (store) => selectMatchState(store).currentTurn;
export const selectStorefront = (store) => selectMatchState(store).storefront;

export const selectWinner = (store) => store[MATCH_STATE].winner;

export const selectDateTurnWillEnd = (store) =>
  new Date(store[MATCH_STATE].turnWillEndAt);
