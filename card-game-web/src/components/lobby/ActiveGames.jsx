import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchActiveGames,
  selectActiveGames,
} from "../../store/lobby/lobby-store";

const ActiveGames = () => {
  const dispatch = useDispatch();
  const activeGames = useSelector(selectActiveGames);

  useEffect(() => {
    dispatch(fetchActiveGames());
    const poller = setInterval(() => {
      dispatch(fetchActiveGames());
    }, 30000);
    return () => {
      clearInterval(poller);
    };
  }, [dispatch]);

  if (!activeGames) {
    return null;
  }

  return `${activeGames} game${
    activeGames > 1 ? "s" : ""
  } being played right now`;
};

export default ActiveGames;
