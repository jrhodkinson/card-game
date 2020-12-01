import styled from "styled-components";
import * as c from "./colors";

export const Button = styled.button`
  font-weight: 500;
  margin: 3px;
  padding: 0 12px;
  background-color: ${c.lightGrey};
  color: ${c.textOnBlack};
  border-radius: 3px;
  cursor: pointer;
  outline: none;
  height: 30px;
  line-height: 30px;
  border: 1px solid ${c.mediumGrey};
  box-shadow: ${c.darkestBlack} 0 3px 2px -2px;

  &:hover {
    background-color: ${c.mediumGrey};
  }
`;

export const BigButton = styled(Button)`
  padding: 0 20px;
  height: 40px;
  line-height: 40px;
  font-size: 1.05em;
  margin: 5px;
`;
