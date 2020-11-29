import styled from "styled-components";
import * as c from "../../colors";

export const Wrapper = styled.div`
  display: flex;
  height: 100vh;
  flex-direction: column;
`;

export const Header = styled.header`
  background-color: ${c.darkestBlack};
  color: ${c.textOnBlack};
`;

export const Main = styled.main`
  background-color: ${c.darkBlack};
  color: ${c.textOnBlack};
  flex: 1;
  overflow: auto;
`;

export const Footer = styled.footer`
  background-color: ${c.darkestBlack};
  color: ${c.faintTextOnBlack};
`;
