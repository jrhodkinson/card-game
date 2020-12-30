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
      <p>The game is currently down for essential maintenance.</p>
      <p>
        Please join the{" "}
        <Link text="Discord" link="https://discord.gg/52RjnmSrHQ" />
        {" or "}
        <Link
          text="subreddit"
          link="https://www.reddit.com/r/revelation218/"
        />{" "}
        for updates.
      </p>
    </Message>
  </Wrapper>
);

export default GameOffline;
