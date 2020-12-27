import React from "react";
import Countdown from "react-countdown";
import { useSelector } from "react-redux";
import styled, { keyframes } from "styled-components";
import { endTurn } from "../../gateway/ws";
import {
  selectCurrentTurn,
  selectDateTurnWillEnd,
} from "../../store/match/match-selector";
import Cards from "../card/Cards";
import * as S from "../styles/Button.styles";
import { MAIN_COLUMN_WIDTH_PX } from "../styles/dimensions";

const Wrapper = styled.div`
  text-align: center;
  display: grid;
  grid-column: 1 / 4;
  grid-template-columns: 200px minmax(${MAIN_COLUMN_WIDTH_PX - 400}px, 1fr) 200px;
  margin: auto;
  align-items: center;
  padding: 10px 0;
`;

const Money = styled.div``;

const pulse = keyframes`
  0%,
  100% {
    transform: scale(1.08);
  }

  50% {
    transform: scale(0.92);
  }
`;

const Timer = styled.div`
  font-size: 2em;
  margin-bottom: 5px;
  animation: 1s ${pulse} infinite;
`;

const CurrentTurn = ({ active }) => {
  const currentTurn = useSelector(selectCurrentTurn);
  const dateTurnWillEnd = useSelector(selectDateTurnWillEnd);
  const countdownRenderer = ({ minutes, seconds }) => {
    const total = 60 * minutes + seconds;
    return total <= 30 ? <Timer>{total}</Timer> : null;
  };
  return (
    <Wrapper>
      <Money>{currentTurn.money}M</Money>
      <Cards cards={currentTurn.playedCards} short animateExit />
      <div>
        <Countdown
          date={dateTurnWillEnd}
          renderer={countdownRenderer}
          intervalDelay={500}
        />
        {active && <S.BigButton onClick={endTurn}>End turn</S.BigButton>}
      </div>
    </Wrapper>
  );
};

export default CurrentTurn;
