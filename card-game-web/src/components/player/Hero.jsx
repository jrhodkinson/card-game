import React from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play-actions";
import { pendingCardRequiresTarget } from "../../store/play-selector";
import { darkCardColor } from "../card/styles/color";

const Wrapper = styled.div`
  align-items: center;
  justify-content: center;
  display: flex;
  background-color: #ccc;
  padding: 5px;
  width: 100%;

  ${({ interactable }) => interactable && "cursor: pointer"};
`;

const Name = styled.div`
  font-weight: bold;
`;

const healthDiameter = 36;

const Health = styled.div`
  background-color: ${darkCardColor("RED")};
  color: white;
  margin-left: 10px;
  text-align: center;
  line-height: ${healthDiameter}px;
  width: ${healthDiameter}px;
  height: ${healthDiameter}px;
  border-radius: ${healthDiameter / 2}px;
`;

const Hero = ({ entityId, name, health }) => {
  const dispatch = useDispatch();
  const heroIsInteractable = useSelector(pendingCardRequiresTarget);
  const handleClick = () => dispatch(selectedTarget(entityId));
  return (
    <Wrapper interactable={heroIsInteractable} onClick={handleClick}>
      <Name>{name}</Name>
      <Health>{health}</Health>
    </Wrapper>
  );
};

export default Hero;
