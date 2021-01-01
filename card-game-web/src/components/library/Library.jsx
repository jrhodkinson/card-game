import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { getCards, getStructures } from "../../gateway/assets";

const Wrapper = styled.div``;

const Library = () => {
  const [cards, setCards] = useState([]);
  const [structures, setStructures] = useState([]);

  useEffect(() => {
    getCards().then(setCards);
    getStructures().then(setStructures);
  }, []);

  return (
    <Wrapper>
      Cards:
      <pre>{JSON.stringify(cards)}</pre>
      Structures:
      <pre>{JSON.stringify(structures)}</pre>
    </Wrapper>
  );
};

export default Library;
