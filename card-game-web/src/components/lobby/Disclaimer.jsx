import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";

const Wrapper = styled.div`
  background-color: ${c.offWhite};
  color: ${c.textOnWhite};
  width: 100%;
  margin-bottom: 20px;
  padding: 20px 20% 15px;
  border-radius: 3px;
  text-align: center;
  font-size: 1.1em;
  box-shadow: ${c.darkestBlack} 0 0 5px 5px;

  strong {
    font-weight: 700;
  }

  p {
    margin-bottom: 10px;
    line-height: 160%;
    font-weight: 300;
    word-break: keep-all;

    &.signature {
      text-align: right;
      font-style: italic;
      margin-top: -10px;
    }
  }
`;

const Disclaimer = () => (
  <Wrapper>
    <p>
      Revelation 21:8 is in <strong>alpha</strong> — unpolished and incomplete.
    </p>
    <p>Your feedback is instrumental in improving this game.</p>
    <p>Updates on the 1st and 3rd Saturday of each month.</p>
    <p>Thank you for taking the time to play.</p>
    <p className="signature">~ RD</p>
  </Wrapper>
);

export default Disclaimer;
