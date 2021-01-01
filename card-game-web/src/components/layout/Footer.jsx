import React from "react";
import ActiveMatchCount from "../lobby/ActiveMatchCount";
import * as S from "./styles/Footer.styles";

const Link = ({ text, link, sameTab = false }) => {
  return (
    <S.Link>
      {sameTab ? (
        <a href={link}>{text}</a>
      ) : (
        <a href={link} target="_blank" rel="noopener noreferrer">
          {text}
        </a>
      )}
    </S.Link>
  );
};

const Footer = () => {
  return (
    <S.Wrapper>
      <S.Links>
        <Link text="Discord" link="https://discord.gg/52RjnmSrHQ" />
        <Link text="Reddit" link="https://www.reddit.com/r/revelation218/" />
        <Link text="Library" link="/library" sameTab />
      </S.Links>
      <S.ActiveMatchCount>
        <ActiveMatchCount />
      </S.ActiveMatchCount>
    </S.Wrapper>
  );
};

export default Footer;
