import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";

const Wrapper = styled.input`
  background-color: ${c.offWhite};
  border: 1px solid ${c.darkestBlack};
  border-radius: 3px;
  color: ${c.textOnWhite};
  padding: 0 5px;
  box-shadow: ${c.darkestBlack} 0 3px 2px -2px;
  outline: none;

  ${({ large }) => {
    if (large) {
      return `
        height: 40px;
        line-height: 40px;
        padding: 0 8px;
        margin: 5px;
      `;
    }
    return `
      height: 30px;
      line-height: 30px;
      padding: 0 5px;
      margin: 3px;
    `;
  }};

  ::placeholder {
    color: ${c.faintTextOnWhite};
    opacity: 1;
  }

  :-ms-input-placeholder {
    color: ${c.faintTextOnWhite};
  }

  ::-ms-input-placeholder {
    color: ${c.faintTextOnWhite};
  }
`;

const TextInput = React.forwardRef(
  ({ name, placeholder, large = false, password = false }, ref) => (
    <Wrapper
      name={name}
      type={password ? "password" : "text"}
      placeholder={placeholder}
      large={large}
      ref={ref}
    />
  )
);

export default TextInput;
