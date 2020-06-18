import React from "react";
import { useSelector } from "react-redux";
import { getCurrentTurn } from "../store/match-selector";
import Cards from "./card/Cards";

const CurrentTurn = () => {
  const currentTurn = useSelector(getCurrentTurn);
  return (
    <>
      {currentTurn.money} Money
      <Cards cards={currentTurn.playedCards} />
    </>
  );
};

export default CurrentTurn;
