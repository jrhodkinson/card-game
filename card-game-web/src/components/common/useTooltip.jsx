import React, { useEffect, useState } from "react";
import ReactTooltip from "react-tooltip";
import * as c from "../styles/colors";
import { v4 as uuidv4 } from "uuid";

const useTooltip = () => {
  const [id, setId] = useState();

  useEffect(() => {
    setId(uuidv4());
  }, []);

  return {
    id,
    tooltip: (
      <ReactTooltip
        id={id}
        backgroundColor={c.darkestBlack}
        textColor={c.textOnBlack}
        effect="solid"
      />
    ),
  };
};

export default useTooltip;
