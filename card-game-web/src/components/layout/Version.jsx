import { useEffect, useState } from "react";
import { getVersion } from "../../gateway/version";

const Version = () => {
  const [version, setVersion] = useState();
  useEffect(() => {
    getVersion().then((response) => {
      setVersion(response.data.version);
    });
  }, []);

  if (!version) {
    return null;
  }

  return version;
};

export default Version;
