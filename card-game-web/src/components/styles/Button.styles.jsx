import styled from "styled-components";
import * as c from "../colors";

const Button = styled.button`
  font-weight: 500;
  border: none;
  margin: 0;
  padding: 10px 20px;
  background-color: ${c.lightGrey};
  color: ${c.textOnBlack};
  border-radius: 5px;
  cursor: pointer;
  outline: none;

  &:hover {
    background-color: ${c.mediumGrey};
  }
`;

export default Button;
