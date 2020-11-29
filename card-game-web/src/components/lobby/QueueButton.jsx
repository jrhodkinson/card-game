import React from "react";
import { useDispatch } from "react-redux";
import { queue } from "../../store/lobby/lobby-store";
import { BigButton } from "../styles/Button.styles";

const QueueButton = () => {
  const dispatch = useDispatch();
  return <BigButton onClick={() => dispatch(queue())}>Queue</BigButton>;
};

export default QueueButton;
