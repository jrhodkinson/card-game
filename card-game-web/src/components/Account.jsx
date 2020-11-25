import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { login, selectUser, whoAmI } from "../store/account/account-store";

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

  return (
    <>
      <input type="text" placeholder="Username" onKeyUp={handleUsernameKeyUp} />
      <div>Logged in as {user}</div>
    </>
  );
};

export default Account;
