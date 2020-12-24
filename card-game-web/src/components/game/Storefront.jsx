import React from "react";
import * as c from "../styles/colors";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectStorefront } from "../../store/match/match-selector";
import { selectedCardInStorefront } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireStoreTarget } from "../../store/play/play-selector";
import Cards from "../card/Cards";

const Wrapper = styled.div`
  background-color: ${c.mediumGrey};
  grid-column: 1 / 4;
  padding: 10px;
  box-shadow: 0 0 3px -3px ${c.darkBlack};
`;

const Storefront = ({ active }) => {
  const storefront = useSelector(selectStorefront);
  const selectedCardRequiresStoreFront = useSelector(
    selectDoesPendingCardRequireStoreTarget
  );
  const dispatch = useDispatch();
  const handleCardClick = (card) => {
    dispatch(selectedCardInStorefront(card));
  };
  return (
    <Wrapper>
      <Cards
        cards={storefront.row}
        short
        animateEntry
        isInteractable={() => active || selectedCardRequiresStoreFront}
        shaking={selectedCardRequiresStoreFront}
        onCardClick={handleCardClick}
      />
    </Wrapper>
  );
};

export default Storefront;
