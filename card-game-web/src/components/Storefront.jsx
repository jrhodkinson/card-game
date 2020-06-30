import React from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { getStorefront } from "../store/match-selector";
import { selectedCardInStorefront } from "../store/play-actions";
import Cards from "./card/Cards";

const Wrapper = styled.div`
  background-color: #c8c8c8;
  padding: 10px;
`;

const Storefront = () => {
  const storefront = useSelector(getStorefront);
  const dispatch = useDispatch();
  const handleCardClick = (card) => dispatch(selectedCardInStorefront(card));
  return (
    <Wrapper>
      <Cards
        cards={storefront.row}
        hideDescriptions
        interactable
        onCardClick={handleCardClick}
      />
    </Wrapper>
  );
};

export default Storefront;
