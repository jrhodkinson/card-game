import React, { useEffect, useState } from "react";
import ReactDOM from "react-dom";
import ReactTooltip from "react-tooltip";
import { v4 as uuidv4 } from "uuid";
import { root } from "../../index";
import * as c from "../styles/colors";

const useTooltip = ({ fixed = true } = {}) => {
  const [id, setId] = useState(uuidv4());

  useEffect(() => {
    setId(uuidv4());
    ReactTooltip.rebuild();
  }, []);

  return {
    id,
    tooltip: ReactDOM.createPortal(
      <ReactTooltip
        id={id}
        backgroundColor={c.darkestBlack}
        textColor={c.textOnBlack}
        effect={fixed ? "solid" : "float"}
      />,
      root
    ),
  };
};

export default useTooltip;
