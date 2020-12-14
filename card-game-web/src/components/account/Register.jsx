import React, { useRef } from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { postRegister } from "../../gateway/account";
import {
  logout,
  receivedAccountDetails,
} from "../../store/account/account-store";
import SubmitInput from "../common/SubmitInput";
import TextInput from "../common/TextInput";
import * as c from "../styles/colors";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";

const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  color: ${c.textOnBlack};
  margin: auto;
  padding-bottom: 5%;
  display: flex;
  height: 100%;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const Header = styled.div`
  font-weight: 500;
  font-size: 2em;
  margin-bottom: 20px;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  width: 400px;
  padding: 15px;
  border-radius: 3px;
  background-color: ${c.lightestGrey};
  color: ${c.textOnGrey};
`;

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 0 5px 0;

  &:last-of-type {
    margin-bottom: 15px;
  }
`;

const ErrorWrapper = styled.p`
  color: ${c.red};
  font-size: 0.85em;
  margin: 0 5px 6px;
`;

const Register = () => {
  const { register, handleSubmit, errors, watch, setError } = useForm();
  const password = useRef({});
  password.current = watch("password");
  const dispatch = useDispatch();

  const onSubmit = (data) => {
    const { username, email, password } = data;
    dispatch(logout());
    postRegister(username, email, password)
      .then(({ data }) => {
        dispatch(receivedAccountDetails(data));
      })
      .catch((error) => {
        const data = error.response.data;
        if (data.reason) {
          switch (data.reason) {
            case "username":
              setError("username", {
                type: "server",
                message: data.details,
              });
              break;
            case "email":
              setError("email", {
                type: "server",
                message: data.details,
              });
              break;
            case "password":
              setError("password", {
                type: "server",
                message: data.details,
              });
              break;
            default:
            // not supported
          }
        }
      });
  };

  return (
    <Wrapper>
      <Header>Register to play</Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <InputWrapper>
          <TextInput
            name="username"
            label="Username"
            large
            ref={register({
              required: "Username required",
              minLength: {
                value: 3,
                message: "Username must be at least 3 characters",
              },
            })}
          />
          {errors.username && (
            <ErrorWrapper>{errors.username.message}</ErrorWrapper>
          )}
        </InputWrapper>
        <InputWrapper>
          <TextInput
            name="email"
            label="Email"
            large
            ref={register({
              required: "Email address required",
            })}
          />
          {errors.email && <ErrorWrapper>{errors.email.message}</ErrorWrapper>}
        </InputWrapper>
        <InputWrapper>
          <TextInput
            name="password"
            label="Password"
            large
            password
            ref={register({
              required: "Password required",
              minLength: {
                value: 8,
                message: "Password must be at least 8 characters",
              },
            })}
          />
          {errors.password && (
            <ErrorWrapper>{errors.password.message}</ErrorWrapper>
          )}
        </InputWrapper>
        <InputWrapper>
          <TextInput
            name="password_repeated"
            label="Password, again"
            large
            password
            ref={register({
              validate: (value) =>
                value === password.current || "Passwords don't match",
            })}
          />
          {errors.password_repeated && (
            <ErrorWrapper>{errors.password_repeated.message}</ErrorWrapper>
          )}
        </InputWrapper>
        <SubmitInput large value="Register" />
      </Form>
    </Wrapper>
  );
};

export default Register;
