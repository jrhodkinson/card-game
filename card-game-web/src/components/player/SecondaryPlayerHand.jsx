import React from "react";
import CardStump from "../card/CardStump";

const SecondaryPlayerHand = ({ size }) =>
  [...Array(size)].map((e, i) => <CardStump key={i} />);

export default SecondaryPlayerHand;
