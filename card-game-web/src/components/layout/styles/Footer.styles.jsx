import styled from "styled-components";
import * as c from "../../styles/colors";
import { MAIN_COLUMN_WIDTH } from "../../styles/dimensions";

export const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  padding: 10px 10px 8px;
  display: flex;
  align-items: center;
`;

export const SocialLinks = styled.ul`
  margin: 0;
  padding: 0;
  list-style-type: none;
  display: flex;
  flex-direction: row;
`;

export const SocialLink = styled.li`
  font-size: 0.85em;
  margin-right: 12px;

  a {
    text-decoration: none;
    color: ${c.faintTextOnBlack};

    &:hover {
      color: ${c.textOnBlack};
    }
  }
`;

export const ActiveMatchCount = styled.div`
  margin-left: auto;
  font-size: 0.8em;
`;
