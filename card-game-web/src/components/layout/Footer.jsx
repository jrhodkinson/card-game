import React from "react";
import ActiveMatches from "../lobby/ActiveMatches";
import * as S from "./styles/Footer.styles";

const SocialLink = ({ text, link }) => {
  return (
    <S.SocialLink>
      <a href={link} target="_blank" rel="noopener noreferrer">
        {text}
      </a>
    </S.SocialLink>
  );
};

const Footer = () => {
  return (
    <S.Wrapper>
      <S.SocialLinks>
        <SocialLink text="Discord" link="https://discord.gg/52RjnmSrHQ" />
        <SocialLink
          text="Reddit"
          link="https://www.reddit.com/r/revelation218/"
        />
      </S.SocialLinks>
      <S.ActiveMatches>
        <ActiveMatches />
      </S.ActiveMatches>
    </S.Wrapper>
  );
};

export default Footer;
