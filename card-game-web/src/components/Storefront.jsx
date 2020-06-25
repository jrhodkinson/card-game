import React from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import { buyCard } from "../gateway/ws";
import { getStorefront } from "../store/match-selector";
import Cards from "./card/Cards";

const Wrapper = styled.div`
  background-color: #c8c8c8;
  padding: 10px;
`;

const Storefront = () => {
  const storefront = useSelector(getStorefront);
  const handleCardClick = (card) => {
    const { instanceId } = card;
    buyCard(instanceId);
  };
  return (
    <Wrapper>
      <Cards cards={storefront.row} selectable onCardClick={handleCardClick} />
    </Wrapper>
  );
};

export default Storefront;
