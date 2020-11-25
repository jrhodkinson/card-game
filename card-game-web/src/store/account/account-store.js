import Immutable from "seamless-immutable";
import { getMe, postLogin } from "../../gateway/account";

export const ACCOUNT_STATE = "account";
export const NAMESPACE = "account";

export const defaultState = Immutable({
  accountId: undefined,
  name: undefined,
});

export const RECEIVED_ACCOUNT_DETAILS = `${NAMESPACE}/RECEIVED_ACCOUNT_DETAILS`;

export default (state = defaultState, action) => {
  switch (action.type) {
    case RECEIVED_ACCOUNT_DETAILS:
      return state.merge(action.account);
    default:
      return state;
  }
};

export const whoAmI = () => (dispatch) => {
  getMe().then((response) => {
    dispatch({ type: RECEIVED_ACCOUNT_DETAILS, account: response.data });
  });
};

export const login = (username) => (dispatch) => {
  postLogin(username).then((response) => {
    dispatch({ type: RECEIVED_ACCOUNT_DETAILS, account: response.data });
  });
};

export const selectUser = (store) => store[ACCOUNT_STATE].name;
