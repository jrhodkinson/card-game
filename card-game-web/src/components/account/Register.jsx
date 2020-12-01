import React from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { registerAccount } from "../../store/account/account-store";

const Register = () => {
  const { register, handleSubmit } = useForm();
  const dispatch = useDispatch();

  const onSubmit = (data) => {
    dispatch(registerAccount(data.name, data.email, data.password));
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input
        name="name"
        placeholder="Username"
        ref={register({ required: true })}
      />
      <input
        name="email"
        placeholder="Email"
        ref={register({ required: true })}
      />
      <input
        name="password"
        type="password"
        placeholder="Password"
        ref={register({ required: true })}
      />
      <input type="submit" value="Register" />
    </form>
  );
};

export default Register;
