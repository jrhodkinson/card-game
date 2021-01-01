import React from "react";
import { Route, Switch } from "react-router-dom";
import GameMain from "../lobby/GameMain";
import Library from "../library/Library";

const Main = () => (
  <Switch>
    <Route path="/library">
      <Library />
    </Route>
    <Route path="/">
      <GameMain />
    </Route>
  </Switch>
);

export default Main;
