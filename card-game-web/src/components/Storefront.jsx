import React from "react";
import styled from "styled-components";
import { useSelector } from "react-redux";
import { getStorefront } from "../store/match-selector";
import Cards from "./Cards";

const Wrapper = styled.div`
  background-color: #c8c8c8;
  padding: 10px;
`;

const Storefront = () => {
  const storefront = useSelector(getStorefront);
  return (
    <Wrapper>
      <Cards cards={storefront.row} />
    </Wrapper>
  );
};

export default Storefront;
