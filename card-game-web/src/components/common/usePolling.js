import { useEffect } from "react";

export default (poller, delay) => {
  useEffect(() => {
    poller();
    const intervalId = setInterval(poller, delay);
    return () => {
      clearInterval(intervalId);
    };
  }, [delay, poller]);
};
