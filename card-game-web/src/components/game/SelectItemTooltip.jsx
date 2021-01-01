import React from "react";
import { useSelector } from "react-redux";
import {
  selectDoesPendingCardRequireDamageableTarget,
  selectDoesPendingCardRequireStoreTarget,
} from "../../store/play/play-selector";
import MouseTooltip from "../common/MouseTooltip";

const targetMessage = (damageableTarget, storeTarget) => {
  if (damageableTarget) {
    return "Select a player or structure";
  }

  if (storeTarget) {
    return "Select a card in the market";
  }

  return "";
};

const SelectItemTooltip = () => {
  const damageableTarget = useSelector(
    selectDoesPendingCardRequireDamageableTarget
  );
  const storeTarget = useSelector(selectDoesPendingCardRequireStoreTarget);
  const anyTarget = damageableTarget || storeTarget;
  const message = targetMessage(damageableTarget, storeTarget);
  return <MouseTooltip visible={anyTarget}>{message}</MouseTooltip>;
};

export default SelectItemTooltip;
