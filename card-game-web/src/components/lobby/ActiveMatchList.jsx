import React, { useCallback } from "react";
import styled from "styled-components";
import { Button } from "../styles/Button.styles";
import * as c from "../styles/colors";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchAllActiveMatches,
  selectAllActiveMatches,
  spectateMatch,
} from "../../store/lobby/lobby-store";
import usePolling from "../common/usePolling";

const NoActiveMatches = styled.div`
  margin-top: 20px;
`;

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
    dispatch(fetchAllActiveMatches());
  }, [dispatch]);

  usePolling(callback, 15000);

  if (activeMatches.length === 0) {
    return (
      <NoActiveMatches>No games are being played right now</NoActiveMatches>
    );
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
            <Button onClick={() => dispatch(spectateMatch(match.id))}>
              Spectate
            </Button>
          </ActiveMatch>
        ))}
      </List>
    </Wrapper>
  );
};

export default ActiveMatchList;
