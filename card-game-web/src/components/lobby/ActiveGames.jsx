import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchActiveGamesCount,
  selectActiveGames,
} from "../../store/lobby/lobby-store";

const ActiveGames = () => {
  const dispatch = useDispatch();
  const activeGames = useSelector(selectActiveGames);

  useEffect(() => {
    dispatch(fetchActiveGamesCount());
    const poller = setInterval(() => {
      dispatch(fetchActiveGamesCount());
    }, 30000);
    return () => {
      clearInterval(poller);
    };
  }, [dispatch]);

  return `${activeGames} game${
    activeGames === 1 ? "" : "s"
  } being played right now`;
};

export default ActiveGames;
