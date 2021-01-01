import React from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import {
  selectIsGameOffline,
  selectIsQueueing,
} from "../../store/lobby/lobby-store";
import ActiveMatchList from "./ActiveMatchList";
import Disclaimer from "./Disclaimer";
import GameOffline from "./GameOffline";
import QueueButton from "./QueueButton";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";
import InQueue from "./InQueue";

const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  padding: 30px 10px 0;
  display: flex;
  height: 100%;
  align-items: center;
  flex-direction: column;
`;

const Lobby = () => {
  const isGameOffline = useSelector(selectIsGameOffline);
  const isQueueing = useSelector(selectIsQueueing);
  return (
    <Wrapper>
      {isGameOffline ? (
        <GameOffline />
      ) : (
        <>
          <Disclaimer />
          {isQueueing ? <InQueue /> : <QueueButton />}
        </>
      )}
      <ActiveMatchList />
    </Wrapper>
  );
};

export default Lobby;
