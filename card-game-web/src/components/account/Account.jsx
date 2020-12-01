import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import * as S from "../styles/Button.styles";
import { useDispatch, useSelector } from "react-redux";
import {
  login,
  logout,
  selectUser,
  whoAmI,
} from "../../store/account/account-store";

const Account = () => {
  const { register, handleSubmit } = useForm();
  const user = useSelector(selectUser);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(whoAmI());
  }, [dispatch]);

  const onSubmit = (data) => {
    dispatch(login(data.name, data.password));
  };

  const loginUser = (user) => () => dispatch(login(user, user + user));

  if (user) {
    return (
      <>
        <div>Logged in as {user}</div>
        <S.Button onClick={loginUser("jack")}>jack</S.Button>
        <S.Button onClick={loginUser("terry")}>terry</S.Button>
        <S.Button onClick={() => dispatch(logout())}>logout</S.Button>
      </>
    );
  }

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <input
          name="name"
          placeholder="Username or Email"
          ref={register({ required: true })}
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          ref={register({ required: true })}
        />
        <input type="submit" value="Login" />
      </form>
      <button onClick={loginUser("jack")}>jack</button>
      <button onClick={loginUser("terry")}>terry</button>
    </>
  );
};

export default Account;
