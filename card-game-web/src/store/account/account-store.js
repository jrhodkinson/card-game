import Immutable from "seamless-immutable";
import { postLogin } from "../../gateway/account";

export const ACCOUNT_STATE = "account";
export const NAMESPACE = "account";

export const defaultState = Immutable({
  accountId: undefined,
  name: undefined,
});

export const LOGGED_IN = `${NAMESPACE}/LOGGED_IN`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case LOGGED_IN:
      return state.merge(action.account);
    default:
      return state;
  }
};

export const login = (username) => (dispatch) => {
  postLogin(username).then((response) => {
    dispatch({ type: LOGGED_IN, account: response.data });
  });
};

export const selectUser = (store) => store[ACCOUNT_STATE].name;
