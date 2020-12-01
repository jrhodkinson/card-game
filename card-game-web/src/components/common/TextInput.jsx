import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";

const Wrapper = styled.input`
  background-color: ${c.white};
  border: 1px solid ${c.darkestBlack};
  border-radius: 3px;
  color: ${c.textOnWhite};
  padding: 0 5px;
  margin: 3px;
  height: 30px;
  line-height: 30px;
  box-shadow: ${c.darkestBlack} 0 3px 2px -2px;

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

const TextInput = ({ name, placeholder, ref, password = false }) => (
  <Wrapper
    name={name}
    type={password ? "password" : "text"}
    placeholder={placeholder}
    ref={ref}
  />
);

export default TextInput;
