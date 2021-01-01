import { useSelector } from "react-redux";
import { selectIsQueueing } from "../../store/lobby/lobby-store";

const QueueStatus = () => {
  const isQueueing = useSelector(selectIsQueueing);
  return isQueueing ? "Queueing" : null;
};

export default QueueStatus;
