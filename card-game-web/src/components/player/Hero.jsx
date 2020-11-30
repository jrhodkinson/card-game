import React from "react";
import * as c from "../styles/colors";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireTarget } from "../../store/play/play-selector";
import Health from "../common/Health";

const Wrapper = styled.div`
  align-items: center;
  justify-content: center;
  display: flex;
  background-color: ${c.darkGrey};
  color: ${c.textOnBlack};
  padding: 7px 0 7px 7px;
  width: 100%;

  ${({ interactable }) =>
    interactable &&
    `
      background-color: ${c.mediumGrey};
      cursor: pointer;
      
      &:hover {
        background-color: ${c.lightGrey};
        transform: scale(1.05) translate(0, -1.5%);
      }
    `};
`;

const Name = styled.div`
  color: ${c.faintTextOnBlack};
  margin-right: 12px;
`;

const ActiveName = styled(Name)`
  color: ${c.textOnBlack};
  font-weight: 500;
`;

const Hero = ({ entityId, name, health, active }) => {
  const dispatch = useDispatch();
  const heroIsInteractable = useSelector(selectDoesPendingCardRequireTarget);
  const handleClick = () => dispatch(selectedTarget(entityId));
  return (
    <Wrapper interactable={heroIsInteractable} onClick={handleClick}>
      {active ? <ActiveName>{name}</ActiveName> : <Name>{name}</Name>}
      <Health health={health} />
    </Wrapper>
  );
};

export default Hero;
