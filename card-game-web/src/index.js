import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router } from "react-router-dom";
import configureAxios from "./gateway/configureAxios";
import { Provider } from "react-redux";
import App from "./components/App";
import configureStore from "./store/configureStore";

configureAxios();

const store = configureStore();
export const root = document.getElementById("root");

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <Router>
        <App />
      </Router>
    </Provider>
  </React.StrictMode>,
  root
);
