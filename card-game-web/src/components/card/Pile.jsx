import React from "react";
import Card from "./Card";

const Pile = ({ cards }) => {
  return cards.length === 0 ? "Empty" : <Card card={cards[cards.length - 1]} />;
};

export default Pile;
