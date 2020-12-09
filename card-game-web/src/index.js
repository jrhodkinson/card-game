import React from "react";
import ReactDOM from "react-dom";
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
      <App />
    </Provider>
  </React.StrictMode>,
  root
);
