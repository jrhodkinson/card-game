import React from "react";
import { BigButton, Button } from "../styles/Button.styles";

const SubmitInput = ({ value, large = false }) => {
  if (large) {
    return <BigButton as="input" type="submit" value={value} />;
  }
  return <Button as="input" type="submit" value={value} />;
};

export default SubmitInput;
