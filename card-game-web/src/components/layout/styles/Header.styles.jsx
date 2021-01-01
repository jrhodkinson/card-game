import styled from "styled-components";
import * as c from "../../styles/colors";
import { MAIN_COLUMN_WIDTH } from "../../styles/dimensions";

export const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  display: grid;
  grid-template-columns: min-content 1fr;
  padding: 10px;
`;

export const Left = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  white-space: nowrap;
  margin: auto 0;
`;

export const Brand = styled.div`
  cursor: pointer;
  font-size: 1.4em;
  font-weight: 500;
  margin-right: 10px;

  &:hover {
    color: ${c.lightestGrey};
  }
`;

export const Version = styled.div`
  color: ${c.faintTextOnBlack};
`;

export const Account = styled.div`
  display: flex;
  align-items: center;
  margin-left: auto;
  font-size: 0.9em;
`;
