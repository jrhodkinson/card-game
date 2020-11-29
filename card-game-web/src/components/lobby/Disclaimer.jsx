import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";

const Wrapper = styled.div`
  background-color: ${c.offWhite};
  opacity: 0.9;
  color: ${c.textOnWhite};
  width: 100%;
  margin-bottom: 35px;
  padding: 20px 150px;
  border-radius: 10px;
  text-align: center;
  font-size: 1.1em;

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
      Revelation 21:8 is in <strong>pre-alpha</strong> — unpolished and
      incomplete.
    </p>
    <p>Thank you for taking the time to play it.</p>
    <p>
      Unfortunately there's a chance that bugs slip through the net. For these,
      I apologise, and request that you report them via the{" "}
      <strong>#bug&#8209;reports</strong> Discord channel.
    </p>
    <p>Your feedback is instrumental in improving this game.</p>
    <p className="signature">~ RD</p>
  </Wrapper>
);

export default Disclaimer;
