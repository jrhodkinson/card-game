import React from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import * as c from "../styles/colors";
import { registerAccount } from "../../store/account/account-store";
import SubmitInput from "../common/SubmitInput";
import TextInput from "../common/TextInput";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";

const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  color: ${c.textOnBlack};
  margin: 0 auto;
  padding: 0 10px 10%;
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
  width: 300px;

  input {
    margin: 5px 0;
  }

  input:last-of-type {
    margin-top: 10px;
  }
`;

const Register = () => {
  const { register, handleSubmit } = useForm();
  const dispatch = useDispatch();

  const onSubmit = (data) => {
    dispatch(registerAccount(data.name, data.email, data.password));
  };

  return (
    <Wrapper>
      <Header>Register to play</Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <TextInput
          name="name"
          placeholder="Username"
          large
          ref={register({ required: true })}
        />
        <TextInput
          name="email"
          placeholder="Email"
          large
          ref={register({ required: true })}
        />
        <TextInput
          name="password"
          placeholder="Password"
          large
          password
          ref={register({ required: true })}
        />
        <SubmitInput large value="Register" />
      </Form>
    </Wrapper>
  );
};

export default Register;
