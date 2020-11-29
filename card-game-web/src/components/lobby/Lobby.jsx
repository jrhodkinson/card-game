import React from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import { selectIsQueueing } from "../../store/lobby/lobby-store";
import Disclaimer from "./Disclaimer";
import QueueButton from "./QueueButton";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";
import InQueue from "./InQueue";

const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  padding: 0 10px 10% 0;
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const Lobby = () => {
  const isQueueing = useSelector(selectIsQueueing);
  return (
    <Wrapper>
      <Disclaimer />
      {isQueueing ? <InQueue /> : <QueueButton />}
    </Wrapper>
  );
};

export default Lobby;
