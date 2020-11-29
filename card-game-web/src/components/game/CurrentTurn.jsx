import React from "react";
import styled from "styled-components";
import { useSelector } from "react-redux";
import { endTurn } from "../../gateway/ws";
import { selectCurrentTurn } from "../../store/match/match-selector";
import Cards from "../card/Cards";
import Button from "../styles/Button.styles";

const Wrapper = styled.div`
  text-align: center;
  display: grid;
  grid-column: 2 / 3;
  grid-template-columns: 200px 1fr 200px;
  align-items: center;
  padding: 10px 0;
`;

const Money = styled.div``;

const CurrentTurn = ({ active }) => {
  const currentTurn = useSelector(selectCurrentTurn);
  return (
    <Wrapper>
      <Money>{currentTurn.money} Money</Money>
      <Cards cards={currentTurn.playedCards} short />
      <div>{active && <Button onClick={endTurn}>End turn</Button>}</div>
    </Wrapper>
  );
};

export default CurrentTurn;
