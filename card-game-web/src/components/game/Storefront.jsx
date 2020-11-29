import React from "react";
import * as c from "../colors";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectStorefront } from "../../store/match/match-selector";
import { selectedCardInStorefront } from "../../store/play/play-actions";
import Cards from "../card/Cards";

const Wrapper = styled.div`
  background-color: ${c.darkGrey};
  grid-column: 1 / 4;
  padding: 10px;
`;

const Storefront = () => {
  const storefront = useSelector(selectStorefront);
  const dispatch = useDispatch();
  const handleCardClick = (card) => dispatch(selectedCardInStorefront(card));
  return (
    <Wrapper>
      <Cards
        cards={storefront.row}
        short
        interactable
        onCardClick={handleCardClick}
      />
    </Wrapper>
  );
};

export default Storefront;
