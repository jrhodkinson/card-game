import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchActiveMatchCount,
  selectActiveMatchCount,
} from "../../store/lobby/lobby-store";

const ActiveMatchCount = () => {
  const dispatch = useDispatch();
  const activeGames = useSelector(selectActiveMatchCount);

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

export default ActiveMatchCount;
