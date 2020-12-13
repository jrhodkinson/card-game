import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";

const LabelWrapper = styled.label`
  font-size: 0.9em;

  ${({ large }) => {
    if (large) {
      return `
        margin: 2px 5px;
      `;
    }
    return `
      margin: 2px 3px;
    `;
  }};
`;

const InputWrapper = styled.input`
  background-color: ${c.offWhite};
  border: 1px solid ${c.darkestBlack};
  border-radius: 3px;
  color: ${c.textOnWhite};
  padding: 0 5px;
  box-shadow: ${c.darkGrey} 0 1px 3px -1px;
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

  ${({ invalid }) => {
    if (invalid) {
      return `
        background-color: ${c.red};
        color: ${c.textOnRed};
        
        ::placeholder {
          color: ${c.textOnRed};
        }
      
        :-ms-input-placeholder {
          color: ${c.textOnRed};
        }
      
        ::-ms-input-placeholder {
          color: ${c.textOnRed};
        }
      `;
    }
  }};
`;

const TextInput = React.forwardRef(
  (
    {
      name,
      placeholder,
      label = null,
      large = false,
      password = false,
      invalid = false,
    },
    ref
  ) => {
    if (label) {
      return (
        <>
          <LabelWrapper htmlFor={name} large={large}>
            {label}
          </LabelWrapper>
          <InputWrapper
            name={name}
            type={password ? "password" : "text"}
            placeholder={placeholder}
            invalid={invalid}
            large={large}
            ref={ref}
          />
        </>
      );
    }
    return (
      <InputWrapper
        name={name}
        type={password ? "password" : "text"}
        placeholder={placeholder}
        invalid={invalid}
        large={large}
        ref={ref}
      />
    );
  }
);

export default TextInput;
