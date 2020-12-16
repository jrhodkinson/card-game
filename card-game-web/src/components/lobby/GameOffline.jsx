import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";
import { MAIN_COLUMN_WIDTH } from "../styles/dimensions";
import Message from "./Message";

const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  padding: 30px 10px 0;
  display: flex;
  height: 100%;
  align-items: center;
  flex-direction: column;
`;

const LinkWrapper = styled.a`
  color: ${c.primaryAccentTextOnWhite};
  font-weight: 500;
  text-decoration: none;
  cursor: pointer;

  &:hover {
    color: ${c.secondaryAccentTextOnWhite};
  }
`;

const Link = ({ text, link }) => {
  return (
    <LinkWrapper href={link} target="_blank" rel="noopener noreferrer">
      {text}
    </LinkWrapper>
  );
};

const GameOffline = () => (
  <Wrapper>
    <Message>
      <p>
        Thank you for registering to play Revelation 21:8. Your input over the
        coming months will shape this game.
      </p>
      <p>
        <strong>
          Doors open Saturday at 19:00 GMT (13:00 CST, Sunday 08:00 NZDT).
        </strong>
      </p>
      <p>
        If you haven't already, please join the{" "}
        <Link text="Discord" link="https://discord.gg/52RjnmSrHQ" />
        {" or "}
        <Link text="subreddit" link="https://www.reddit.com/r/revelation218/" />
        .
      </p>
    </Message>
  </Wrapper>
);

export default GameOffline;
