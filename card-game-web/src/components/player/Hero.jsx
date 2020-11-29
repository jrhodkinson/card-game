import React from "react";
import * as c from "../colors";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireTarget } from "../../store/play/play-selector";

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

const ActiveName = styled.div`
  color: ${c.textOnBlack};
  font-weight: 500;
`;

const Name = styled.div`
  color: ${c.faintTextOnBlack};
`;

const healthDiameter = 32;

const Health = styled.div`
  background-color: ${c.red};
  color: ${c.textOnRed};
  margin-left: 16px;
  text-align: center;
  line-height: ${healthDiameter}px;
  width: ${healthDiameter}px;
  height: ${healthDiameter}px;
  border-radius: ${healthDiameter / 2}px;
`;

const Hero = ({ entityId, name, health, active }) => {
  const dispatch = useDispatch();
  const heroIsInteractable = useSelector(selectDoesPendingCardRequireTarget);
  const handleClick = () => dispatch(selectedTarget(entityId));
  return (
    <Wrapper interactable={heroIsInteractable} onClick={handleClick}>
      {active ? <ActiveName>- {name} -</ActiveName> : <Name>{name}</Name>}
      <Health>{health}</Health>
    </Wrapper>
  );
};

export default Hero;
