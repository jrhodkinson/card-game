import React from "react";
import Footer from "./Footer";
import Header from "./Header";
import Main from "./Main";
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
