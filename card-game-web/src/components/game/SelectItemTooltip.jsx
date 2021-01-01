import React from "react";
import { useSelector } from "react-redux";
import {
  selectDoesPendingCardRequireCardInHandTarget,
  selectDoesPendingCardRequireDamageableTarget,
  selectDoesPendingCardRequireStoreTarget,
} from "../../store/play/play-selector";
import MouseTooltip from "../common/MouseTooltip";

const targetMessage = (damageableTarget, storeTarget, handTarget) => {
  if (damageableTarget) {
    return "Select a player or structure";
  }

  if (storeTarget) {
    return "Select a card in the market";
  }

  if (handTarget) {
    return "Select a card in your hand";
  }

  return "";
};

const SelectItemTooltip = () => {
  const damageableTarget = useSelector(
    selectDoesPendingCardRequireDamageableTarget
  );
  const storeTarget = useSelector(selectDoesPendingCardRequireStoreTarget);
  const handTarget = useSelector(selectDoesPendingCardRequireCardInHandTarget);
  const message = targetMessage(damageableTarget, storeTarget, handTarget);
  return <MouseTooltip visible={message !== ""}>{message}</MouseTooltip>;
};

export default SelectItemTooltip;
