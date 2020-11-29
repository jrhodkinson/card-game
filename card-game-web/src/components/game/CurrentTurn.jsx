import React from "react";
import styled from "styled-components";
import { useSelector } from "react-redux";
import { endTurn } from "../../gateway/ws";
import { selectCurrentTurn } from "../../store/match/match-selector";
import Cards from "../card/Cards";

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
  const currentTurn = useSelector(selectCurrentTurn);
  return (
    <Wrapper>
      <Money>{currentTurn.money} Money</Money>
      <Cards cards={currentTurn.playedCards} hideDescriptions />
      <EndTurnButton onClick={endTurn}>End turn</EndTurnButton>
    </Wrapper>
  );
};

export default CurrentTurn;
