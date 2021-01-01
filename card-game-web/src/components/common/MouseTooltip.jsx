import React, { useState, useEffect, useCallback } from "react";
import styled from "styled-components";
import * as c from "../styles/colors";

const Tooltip = styled.span`
  background-color: ${c.offWhite};
  border: 1px solid ${c.lightestGrey};
  color: ${c.textOnWhite};
  box-shadow: ${c.darkGrey} 0 0 3px 0;
  padding: 5px;
  border-radius: 3px;
`;

const initialised = ({ x, y }) => x !== undefined && y !== undefined;

const MouseTooltip = ({
  visible = true,
  offsetX = 15,
  offsetY = 10,
  children,
}) => {
  const [position, setPosition] = useState({});

  const getTooltipPosition = useCallback(
    ({ clientX: x, clientY: y }) => {
      setPosition({ x, y });
    },
    [setPosition]
  );

  useEffect(() => {
    window.addEventListener("mousemove", getTooltipPosition);
    return () => window.removeEventListener("mousemove", getTooltipPosition);
  });

  const positioning = {
    display: visible && initialised(position) ? "block" : "none",
    position: "fixed",
    top: position.y + offsetY,
    left: position.x + offsetX,
  };

  return (
    <div style={positioning}>
      <Tooltip>{children}</Tooltip>
    </div>
  );
};

export default MouseTooltip;
