import React from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";
import {
  selectDoesPendingCardRequireCardInHandTarget,
  selectDoesPendingCardRequireDamageableTarget,
  selectDoesPendingCardRequireStoreTarget,
} from "../../store/play/play-selector";
import * as c from "../styles/colors";
import MouseTooltip from "../common/MouseTooltip";

const Important = styled.span`
  color: ${c.primaryAccentTextOnWhite};
  font-weight: 500;
`;

const targetMessage = (damageableTarget, storeTarget, handTarget) => {
  if (damageableTarget) {
    return (
      <>
        Select a <Important>player</Important> or{" "}
        <Important>structure</Important>
      </>
    );
  }

  if (storeTarget) {
    return (
      <>
        Select a <Important>card</Important> in the{" "}
        <Important>market</Important>
      </>
    );
  }

  if (handTarget) {
    return (
      <>
        Select a <Important>card</Important> in your <Important>hand</Important>
      </>
    );
  }

  return false;
};

const SelectItemTooltip = () => {
  const damageableTarget = useSelector(
    selectDoesPendingCardRequireDamageableTarget
  );
  const storeTarget = useSelector(selectDoesPendingCardRequireStoreTarget);
  const handTarget = useSelector(selectDoesPendingCardRequireCardInHandTarget);
  const message = targetMessage(damageableTarget, storeTarget, handTarget);
  return <MouseTooltip visible={message}>{message}</MouseTooltip>;
};

export default SelectItemTooltip;
