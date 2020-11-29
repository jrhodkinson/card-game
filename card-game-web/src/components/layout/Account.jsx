import React, { useEffect } from "react";
import * as S from "../styles/Button.styles";
import { useDispatch, useSelector } from "react-redux";
import { login, selectUser, whoAmI } from "../../store/account/account-store";

const Account = () => {
  const user = useSelector(selectUser);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(whoAmI());
  }, [dispatch]);

  const handleUsernameKeyUp = (e) => {
    if (e.key === "Enter") {
      dispatch(login(e.currentTarget.value));
    }
  };

  const loginUser = (user) => () => dispatch(login(user));

  if (user) {
    return (
      <>
        <div>Logged in as {user}</div>
        <S.Button onClick={loginUser("jack")}>jack</S.Button>
        <S.Button onClick={loginUser("terry")}>terry</S.Button>
      </>
    );
  }

  return (
    <>
      <input type="text" placeholder="Username" onKeyUp={handleUsernameKeyUp} />
      <button onClick={loginUser("jack")}>jack</button>
      <button onClick={loginUser("terry")}>terry</button>
    </>
  );
};

export default Account;
