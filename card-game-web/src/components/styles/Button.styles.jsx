import styled from "styled-components";
import * as c from "./colors";

export const Button = styled.button`
  font-weight: 500;
  border: none;
  margin: 3px;
  padding: 4px 8px;
  background-color: ${c.lightGrey};
  color: ${c.textOnBlack};
  border-radius: 5px;
  cursor: pointer;
  outline: none;

  &:hover {
    background-color: ${c.mediumGrey};
  }
`;

export const BigButton = styled(Button)`
  padding: 10px 20px;
`;
