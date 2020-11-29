import styled from "styled-components";
import { MAIN_COLUMN_WIDTH } from "../../styles/dimensions";

export const Wrapper = styled.div`
  width: ${MAIN_COLUMN_WIDTH};
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 1fr;
  padding: 10px;
`;

export const Brand = styled.div`
  display: flex;
  align-items: center;
  font-size: 1.4em;
  font-weight: 500;
`;

export const Account = styled.div`
  display: flex;
  align-items: center;
  margin-left: auto;
  font-size: 0.9em;
`;
