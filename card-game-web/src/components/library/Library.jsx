import React, { useEffect, useState } from "react";
import styled, { css } from "styled-components";
import { getCards, getStructures } from "../../gateway/assets";
import Loading from "../common/Loading";
import * as c from "../styles/colors";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";
import LibraryCard from "./LibraryCard";
import LibraryStructure from "./LibraryStructure";

const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  padding: 0 10px 10px;
`;

const Header = styled.h2`
  margin: 20px 0 0;
`;

const Details = styled.div`
  background-color: ${c.offWhite};
  color: ${c.textOnWhite};
  border-radius: 3px;
  padding: 5px;
  margin: 10px 0;
  display: grid;
  grid-template-columns: min-content min-content 1fr;
`;

export const Element = styled.div`
  padding: 5px 10px;
  white-space: nowrap;

  ${({ strong }) => {
    if (strong) {
      return css`
        font-weight: 500;
      `;
    }
  }};
`;

const Library = () => {
  const [cards, setCards] = useState([]);
  const [structures, setStructures] = useState([]);

  useEffect(() => {
    getCards().then((response) => setCards(response.data));
    getStructures().then((response) => setStructures(response.data));
  }, []);

  return (
    <Wrapper>
      <Header>Cards</Header>
      {cards.length > 0 ? (
        <Details>
          <Element strong>Name</Element>
          <Element strong>Cost</Element>
          <Element strong>Description</Element>
          {cards.map((c) => (
            <LibraryCard key={c.entityId} card={c} />
          ))}
        </Details>
      ) : (
        <Loading />
      )}
      <Header>Structures</Header>
      {structures.length > 0 ? (
        <Details>
          <Element strong>Name</Element>
          <Element strong>Health</Element>
          <Element strong>Description</Element>
          {structures.map((s) => (
            <LibraryStructure key={s.entityId} structure={s} />
          ))}
        </Details>
      ) : (
        <Loading />
      )}
    </Wrapper>
  );
};

export default Library;
