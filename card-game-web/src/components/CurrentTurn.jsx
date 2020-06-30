import React from "react";
import styled from "styled-components";
import * as S from "../styles";
import { useSelector } from "react-redux";
import { endTurn } from "../gateway/ws";
import { getCurrentTurn } from "../store/match-selector";
import Cards from "./card/Cards";

const Wrapper = styled.div`
  text-align: center;
  display: grid;
  grid-template-columns: 200px 1fr 200px;
  padding: 10px 0;
`;

const Money = styled.div``;

const EndTurnButton = styled.button`
  height: 30px;
`;

const CurrentTurn = () => {
  const currentTurn = useSelector(getCurrentTurn);
  return (
    <Wrapper>
      <Money>{currentTurn.money} Money</Money>
      <S.Centered>
        <Cards cards={currentTurn.playedCards} />
      </S.Centered>
      <EndTurnButton onClick={endTurn}>End turn</EndTurnButton>
    </Wrapper>
  );
};

export default CurrentTurn;
