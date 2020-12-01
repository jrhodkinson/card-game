import React from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { registerAccount } from "../../store/account/account-store";
import SubmitInput from "../common/SubmitInput";
import TextInput from "../common/TextInput";

const Register = () => {
  const { register, handleSubmit } = useForm();
  const dispatch = useDispatch();

  const onSubmit = (data) => {
    dispatch(registerAccount(data.name, data.email, data.password));
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <TextInput
        name="name"
        placeholder="Username"
        ref={register({ required: true })}
      />
      <TextInput
        name="email"
        placeholder="Email"
        ref={register({ required: true })}
      />
      <TextInput
        name="password"
        placeholder="Password"
        password
        ref={register({ required: true })}
      />
      <SubmitInput value="Register" />
    </form>
  );
};

export default Register;
