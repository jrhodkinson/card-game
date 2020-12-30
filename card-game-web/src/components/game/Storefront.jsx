import React from "react";
import Card from "../card/Card";
import * as c from "../styles/colors";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectStorefront } from "../../store/match/match-selector";
import { selectedCardInStorefront } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireStoreTarget } from "../../store/play/play-selector";
import Cards from "../card/Cards";

const Wrapper = styled.div`
  grid-column: 1 / 4;
  padding: 10px;
  background-color: ${c.darkGrey};
  box-shadow: 0 0 3px -3px ${c.darkBlack};
  display: flex;
  justify-content: center;
`;

const LabelWrapper = styled.div`
  position: relative;
  margin: 0 10px 0 35px;
`;

const Label = styled.div`
  color: ${c.faintTextOnBlack};
  transform: translateX(-50%) translateY(-50%) rotate(-90deg);
  position: absolute;
  top: 50%;
  left: 50%;
  opacity: 0.8;
  letter-spacing: 1px;
  font-size: 0.9em;
`;

const NextCard = styled.div`
  opacity: 0.5;
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
      <LabelWrapper>
        <Label>Next</Label>
      </LabelWrapper>
      <NextCard>
        <Card card={storefront.next} short displayCost faint />
      </NextCard>
    </Wrapper>
  );
};

export default Storefront;
