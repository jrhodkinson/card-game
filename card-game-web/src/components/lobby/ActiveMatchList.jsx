import React, { useCallback } from "react";
import styled from "styled-components";
import { Button } from "../styles/Button.styles";
import * as c from "../styles/colors";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchAllActiveMatches,
  selectAllActiveMatches,
} from "../../store/lobby/lobby-store";
import usePoll from "../common/usePoll";

const Wrapper = styled.div`
  margin: 10px;
  padding: 0;
  width: 50%;
`;

const Header = styled.h2`
  margin: 10px 0;
`;

const List = styled.ul`
  margin: 0;
  padding: 10px 20px;
  list-style-type: none;
  background-color: ${c.offWhite};
  color: ${c.textOnWhite};
  border-radius: 3px;
  display: block;
  width: 100%;
`;

const ActiveMatch = styled.li`
  padding: 5px;
  display: flex;
  align-items: center;
`;

const Players = styled.div`
  flex-grow: 1;
`;

const Player = styled.span`
  font-weight: bold;
  color: ${c.primaryAccentTextOnWhite};
`;

const ActiveMatchList = () => {
  const dispatch = useDispatch();
  const activeMatches = useSelector(selectAllActiveMatches);
  const callback = useCallback(() => {
    console.log("polling");
    dispatch(fetchAllActiveMatches());
  }, [dispatch]);

  usePoll(callback, 15000);

  if (activeMatches.length === 0) {
    return null;
  }

  return (
    <Wrapper>
      <Header>Active Matches</Header>
      <List>
        {activeMatches.map((match) => (
          <ActiveMatch key={match.id}>
            <Players>
              {match.players
                .map((player) => <Player key={player}>{player}</Player>)
                .reduce((prev, curr) => [prev, " vs ", curr])}
            </Players>
            <Button>Spectate</Button>
          </ActiveMatch>
        ))}
      </List>
    </Wrapper>
  );
};

export default ActiveMatchList;
