import React from "react";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { selectedTarget } from "../../store/play-actions";

const Wrapper = styled.div`
  align-items: center;
  display: flex;
  background-color: #ccc;
  padding: 20px;
`;

const Name = styled.div`
  font-weight: bold;
`;

const healthDiameter = 36;

const Health = styled.div`
  background-color: #f33;
  color: white;
  margin-left: 10px;
  text-align: center;
  line-height: ${healthDiameter}px;
  width: ${healthDiameter}px;
  height: ${healthDiameter}px;
  border-radius: ${healthDiameter / 2}px;
`;

const Hero = ({ instanceId, name, health }) => {
  const dispatch = useDispatch();
  const handleClick = () => dispatch(selectedTarget(instanceId));
  return (
    <Wrapper onClick={handleClick}>
      <Name>{name}</Name>
      <Health>{health}</Health>
    </Wrapper>
  );
};

export default Hero;
