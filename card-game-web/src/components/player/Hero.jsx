import React from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play/play-actions";
import { selectDoesPendingCardRequireTarget } from "../../store/play/play-selector";

const Wrapper = styled.div`
  align-items: center;
  justify-content: center;
  display: flex;
  background-color: #ccc;
  padding: 5px;
  width: 100%;

  ${({ interactable }) => interactable && "cursor: pointer"};
`;

const ActiveName = styled.div`
  font-weight: bold;
  color: red;
`;

const Name = styled.div`
  color: #333;
`;

const healthDiameter = 36;

const Health = styled.div`
  background-color: red;
  color: white;
  margin-left: 10px;
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
      {active ? <ActiveName>{name}</ActiveName> : <Name>{name}</Name>}
      <Health>{health}</Health>
    </Wrapper>
  );
};

export default Hero;
