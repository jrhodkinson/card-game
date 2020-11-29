import React from "react";
import * as c from "../colors";
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
  padding: 7px;
  width: 100%;

  ${({ interactable }) => interactable && "cursor: pointer"};
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
