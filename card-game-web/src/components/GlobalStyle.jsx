import { createGlobalStyle } from "styled-components";
import "typeface-merriweather";

export const GlobalStyle = createGlobalStyle`
  * {
    font-family: Merriweather, serif;
    font-weight: 300;
  }
  
  pre {
    font-family: monospace;
  }
`;
