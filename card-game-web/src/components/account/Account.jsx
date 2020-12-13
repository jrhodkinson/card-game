import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { postLogin } from "../../gateway/account";
import {
  logout,
  receivedAccountDetails,
  selectUser,
  whoAmI,
} from "../../store/account/account-store";
import SubmitInput from "../common/SubmitInput";
import TextInput from "../common/TextInput";
import * as S from "../styles/Button.styles";

const LoggedInAs = styled.div`
  margin-right: 10px;
`;

const Account = () => {
  const { register, handleSubmit, errors, setError } = useForm();
  const user = useSelector(selectUser);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(whoAmI());
  }, [dispatch]);

  const onSubmit = (form) => {
    const { name, password } = form;
    postLogin(name, password)
      .then(({ data }) => {
        dispatch(receivedAccountDetails(data));
      })
      .catch(({ response }) => {
        if (response.status === 403) {
          setError("name", "");
          setError("password", "");
        }
      });
  };

  if (user) {
    return (
      <>
        <LoggedInAs>Logged in as {user}</LoggedInAs>
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
          invalid={errors.name}
          ref={register({ required: true })}
        />
        <TextInput
          name="password"
          placeholder="Password"
          password
          invalid={errors.password}
          ref={register({ required: true })}
        />
        <SubmitInput value="Login" />
      </form>
    </>
  );
};

export default Account;
