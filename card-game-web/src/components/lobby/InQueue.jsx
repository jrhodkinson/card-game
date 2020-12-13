import React from "react";
import { useDispatch } from "react-redux";
import { leaveQueue } from "../../store/lobby/lobby-store";
import { BigButton } from "../styles/Button.styles";

const InQueue = () => {
  const dispatch = useDispatch();
  return (
    <BigButton onClick={() => dispatch(leaveQueue())}>
      Queuing. Click here to leave the queue.
    </BigButton>
  );
};

export default InQueue;
