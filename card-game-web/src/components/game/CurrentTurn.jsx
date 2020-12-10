import React from "react";
import Countdown from "react-countdown";
import styled, { keyframes } from "styled-components";
import { useSelector } from "react-redux";
import { endTurn } from "../../gateway/ws";
import {
  selectCurrentTurn,
  selectDateTurnWillEnd,
} from "../../store/match/match-selector";
import Cards from "../card/Cards";
import * as S from "../styles/Button.styles";

const Wrapper = styled.div`
  text-align: center;
  display: grid;
  grid-column: 2 / 3;
  grid-template-columns: 200px 1fr 200px;
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
    return total <= 15 ? <Timer>{total}</Timer> : null;
  };
  return (
    <Wrapper>
      <Money>{currentTurn.money} Money</Money>
      <Cards cards={currentTurn.playedCards} short />
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
