import React from "react";
import Main from "../lobby/Main";
import Footer from "./Footer";
import Header from "./Header";
import * as S from "./styles/Layout.styles";

const Layout = () => {
  return (
    <S.Wrapper>
      <S.Header>
        <Header />
      </S.Header>
      <S.Main>
        <Main />
      </S.Main>
      <S.Footer>
        <Footer />
      </S.Footer>
    </S.Wrapper>
  );
};

export default Layout;
