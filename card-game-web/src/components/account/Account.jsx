import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import styled from "styled-components";
import SubmitInput from "../common/SubmitInput";
import TextInput from "../common/TextInput";
import * as S from "../styles/Button.styles";
import { useDispatch, useSelector } from "react-redux";
import {
  login,
  logout,
  selectUser,
  whoAmI,
} from "../../store/account/account-store";

const LoggedInAs = styled.div`
  margin-right: 10px;
`;

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
        <LoggedInAs>Logged in as {user}</LoggedInAs>
        <S.Button onClick={loginUser("jack")}>jack</S.Button>
        <S.Button onClick={loginUser("terry")}>terry</S.Button>
        <S.Button onClick={() => dispatch(logout())}>Logout</S.Button>
      </>
    );
  }

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <TextInput
          name="name"
          placeholder="Username or email"
          ref={register({ required: true })}
        />
        <TextInput
          name="password"
          placeholder="Password"
          password
          ref={register({ required: true })}
        />
        <SubmitInput value="Login" />
      </form>
      <S.Button onClick={loginUser("jack")}>jack</S.Button>
      <S.Button onClick={loginUser("terry")}>terry</S.Button>
    </>
  );
};

export default Account;
