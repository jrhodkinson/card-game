import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchActiveMatchCount,
  selectActiveMatches,
} from "../../store/lobby/lobby-store";

const ActiveMatches = () => {
  const dispatch = useDispatch();
  const activeGames = useSelector(selectActiveMatches);

  useEffect(() => {
    dispatch(fetchActiveMatchCount());
    const poller = setInterval(() => {
      dispatch(fetchActiveMatchCount());
    }, 30000);
    return () => {
      clearInterval(poller);
    };
  }, [dispatch]);

  return `${activeGames} game${
    activeGames === 1 ? "" : "s"
  } being played right now`;
};

export default ActiveMatches;
