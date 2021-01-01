import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import GameMain from "../lobby/GameMain";

const Main = () => (
  <Router>
    <Switch>
      <Route path="/library">library</Route>
      <Route path="/">
        <GameMain />
      </Route>
    </Switch>
  </Router>
);

export default Main;
