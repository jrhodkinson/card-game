import React from "react";
import * as S from "./styles/Header.styles";
import Account from "../account/Account";
import Brand from "./Brand";

const Header = () => {
  return (
    <S.Wrapper>
      <S.Brand>
        <Brand />
      </S.Brand>
      <S.Account>
        <Account />
      </S.Account>
    </S.Wrapper>
  );
};

export default Header;
