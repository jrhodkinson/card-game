import React from "react";
import * as S from "./styles/Header.styles";
import Account from "../account/Account";
import Brand from "./Brand";
import Version from "./Version";

const Header = () => {
  return (
    <S.Wrapper>
      <S.Left>
        <S.Brand>
          <Brand />
        </S.Brand>
        <S.Version>
          <Version />
        </S.Version>
      </S.Left>
      <S.Account>
        <Account />
      </S.Account>
    </S.Wrapper>
  );
};

export default Header;
