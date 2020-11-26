import React from "react";
import { useDispatch } from "react-redux";
import { queue } from "../store/lobby/lobby-store";

const QueueButton = () => {
  const dispatch = useDispatch();
  return <button onClick={() => dispatch(queue())}>Queue</button>;
};

export default QueueButton;
