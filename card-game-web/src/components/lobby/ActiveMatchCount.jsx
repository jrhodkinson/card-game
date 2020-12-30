import { useCallback } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  fetchActiveMatchCount,
  selectActiveMatchCount,
} from "../../store/lobby/lobby-store";
import usePoll from "../common/usePoll";

const ActiveMatchCount = () => {
  const dispatch = useDispatch();
  const activeMatchCount = useSelector(selectActiveMatchCount);
  const callback = useCallback(() => {
    dispatch(fetchActiveMatchCount());
  }, [dispatch]);

  usePoll(callback, 30000);

  return `${activeMatchCount} game${
    activeMatchCount === 1 ? "" : "s"
  } being played right now`;
};

export default ActiveMatchCount;
