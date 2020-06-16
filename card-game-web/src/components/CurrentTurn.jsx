import React from "react";
import { useSelector } from "react-redux";
import { getCurrentTurn } from "../store/match-selector";
import Cards from "./Cards";

const CurrentTurn = () => {
  const currentTurn = useSelector(getCurrentTurn);
  return (
    <div className="current-turn">
      <div className="money-count">{currentTurn.money} Money</div>
      <Cards cards={currentTurn.playedCards} />
    </div>
  );
};

export default CurrentTurn;
