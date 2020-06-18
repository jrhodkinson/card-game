import React from "react";
import Cards from "./card/Cards";

const Player = ({ player }) => {
  const { hand, ...rest } = player;
  return (
    <>
      <pre>{JSON.stringify(rest, 0, 4)}</pre>
      <Cards cards={hand} />
    </>
  );
};

export default Player;
