import React from "react";
import Account from "./Account";
import { GlobalStyle } from "./GlobalStyle";
import Lobby from "./Lobby";

const App = () => {
  return (
    <>
      <GlobalStyle />
      <Account />
      <Lobby />
    </>
  );
};

export default App;
