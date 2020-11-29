import React from "react";
import * as S from "./styles";
import Lobby from "../game/Lobby";
import Footer from "./Footer";
import Header from "./Header";

const Layout = () => {
  return (
    <S.Wrapper>
      <S.Header>
        <Header />
      </S.Header>
      <S.Main>
        <Lobby />
      </S.Main>
      <S.Footer>
        <Footer />
      </S.Footer>
    </S.Wrapper>
  );
};

export default Layout;
