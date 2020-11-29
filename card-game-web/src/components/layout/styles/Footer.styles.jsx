import styled from "styled-components";

export const Wrapper = styled.div`
  width: 1100px;
  margin: 0 auto;
  padding: 10px;
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
  margin-right: 10px;

  a {
    text-decoration: none;
    color: #ddd;

    &:hover {
      color: white;
    }
  }
`;
