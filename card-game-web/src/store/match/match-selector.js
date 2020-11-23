import { selectUser } from "../account/account-store";
import { defaultPlayer, MATCH_STATE } from "./match-reducer";

const selectMatchState = (store) => store[MATCH_STATE];

const selectPrimaryUser = (store) => selectUser(store);
const selectSecondaryUser = (store) => {
  const users = Object.keys(selectMatchState(store).players);
  const primaryUser = selectPrimaryUser(store);
  return users.filter((users) => users !== primaryUser)[0];
};

export const selectPrimaryPlayer = (store) =>
  selectMatchState(store).players[selectPrimaryUser(store)] || defaultPlayer;
export const selectSecondaryPlayer = (store) =>
  selectMatchState(store).players[selectSecondaryUser(store)] || defaultPlayer;

export const isPrimaryPlayerActive = (store) =>
  selectPrimaryUser(store) === selectMatchState(store).activeUser;

export const selectCurrentTurn = (store) => selectMatchState(store).currentTurn;
export const selectStorefront = (store) => selectMatchState(store).storefront;

export const selectWinner = (store) => selectMatchState(store).winner;
