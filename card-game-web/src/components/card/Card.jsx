import React from "react";
import Description from "../common/Description";
import * as S from "./styles";

const Card = ({
  card,
  isInteractable = () => false,
  selected = false,
  onCardClick = () => {},
  short = false,
  shaking = false,
  displayCost = true,
}) => {
  return (
    <S.Card
      short={short}
      interactable={isInteractable(card)}
      selected={selected}
      onClick={() => onCardClick(card)}
      shaking={shaking}
    >
      {displayCost && <S.CardCost>{card.cost}</S.CardCost>}
      <S.CardName>{card.name}</S.CardName>
      {short ? (
        <S.ShortCardDescription>
          <Description lines={card.description.lines} />
        </S.ShortCardDescription>
      ) : (
        <>
          <S.CardImage />
          <S.CardDescription>
            <Description lines={card.description.lines} />
          </S.CardDescription>
        </>
      )}
    </S.Card>
  );
};

export default Card;
