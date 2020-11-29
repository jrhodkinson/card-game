import Immutable from "seamless-immutable";
import { getMe, postLogin } from "../../gateway/account";

export const ACCOUNT_STATE = "account";
export const NAMESPACE = "account";

export const defaultState = Immutable({
  initialised: false,
  accountId: undefined,
  name: undefined,
});

export const RECEIVED_ACCOUNT_DETAILS = `${NAMESPACE}/RECEIVED_ACCOUNT_DETAILS`;
export const RECEIVED_NO_ACCOUNT_DETAILS = `${NAMESPACE}/RECEIVED_NO_ACCOUNT_DETAILS`;
export const LOGGED_OUT = `${NAMESPACE}/LOGGED_OUT`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_ACCOUNT_DETAILS:
      return state.merge(action.account).set("initialised", true);
    case RECEIVED_NO_ACCOUNT_DETAILS:
      return defaultState.set("initialised", true);
    case LOGGED_OUT:
      return defaultState;
    default:
      return state;
  }
};

export const whoAmI = () => (dispatch) => {
  getMe()
    .then((response) => {
      dispatch({ type: RECEIVED_ACCOUNT_DETAILS, account: response.data });
    })
    .catch(() => {
      dispatch({ type: RECEIVED_NO_ACCOUNT_DETAILS });
    });
};

export const login = (username) => (dispatch) => {
  dispatch({ type: LOGGED_OUT });
  postLogin(username).then((response) => {
    dispatch({ type: RECEIVED_ACCOUNT_DETAILS, account: response.data });
  });
};

export const selectHaveInitialisedAccountId = (store) =>
  store[ACCOUNT_STATE].initialised;
export const selectAccountId = (store) => store[ACCOUNT_STATE].accountId;
export const selectUser = (store) => store[ACCOUNT_STATE].name;
