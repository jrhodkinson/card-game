import React from "react";
import styled from "styled-components";
import * as c from "../styles/colors";
import Message from "./Message";

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
  <Message>
    <p>
      <strong>Revelation 21:8 is being upgraded.</strong>
    </p>
    <p>
      In progress games will be completed, but no new games will be started.
    </p>
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
);

export default GameOffline;
