import styled from "styled-components";
import * as c from "../../colors";

export const Wrapper = styled.div`
  width: 1100px;
  margin: 0 auto;
  padding: 7px 10px;
`;

export const SocialLinks = styled.ul`
  margin: 0;
  padding: 0;
  list-style-type: none;
  display: flex;
  flex-direction: row;
`;

export const SocialLink = styled.li`
  font-size: 0.8em;
  margin-right: 12px;

  a {
    text-decoration: none;
    color: ${c.faintTextOnBlack};

    &:hover {
      color: ${c.textOnBlack};
    }
  }
`;
