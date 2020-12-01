import React from "react";
import { Button } from "../styles/Button.styles";

const SubmitInput = ({ value }) => (
  <Button as="input" type="submit" value={value} />
);

export default SubmitInput;
