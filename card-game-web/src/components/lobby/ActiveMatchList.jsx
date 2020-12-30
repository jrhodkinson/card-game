import React, { useCallback } from "react";
import styled from "styled-components";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchAllActiveMatches,
  selectAllActiveMatches,
} from "../../store/lobby/lobby-store";
import usePoll from "../common/usePoll";

const List = styled.ul``;

const ActiveMatch = styled.li``;

const ActiveMatchList = () => {
  const dispatch = useDispatch();
  const activeMatches = useSelector(selectAllActiveMatches);
  const callback = useCallback(() => {
    console.log("polling");
    dispatch(fetchAllActiveMatches());
  }, [dispatch]);

  usePoll(callback, 15000);

  return (
    <List>
      {activeMatches.map((match) => (
        <ActiveMatch key={match.id}>{JSON.stringify(match)}</ActiveMatch>
      ))}
    </List>
  );
};

export default ActiveMatchList;
