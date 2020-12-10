import React from "react";
import Description from "../common/Description";
import * as S from "./styles";

const Card = ({
  card,
  isInteractable = () => false,
  selected = false,
  onCardClick = () => {},
  short = false,
}) => {
  return (
    <S.Card
      short={short}
      interactable={isInteractable(card)}
      selected={selected}
      onClick={() => onCardClick(card)}
    >
      <S.CardCost background={card.color}>{card.cost}</S.CardCost>
      <S.CardName background={card.color}>{card.name}</S.CardName>
      {short ? (
        <S.ShortCardDescription background={card.color}>
          <Description description={card.description} />
        </S.ShortCardDescription>
      ) : (
        <>
          <S.CardImage background={card.color} />
          <S.CardDescription background={card.color}>
            <Description description={card.description} />
          </S.CardDescription>
        </>
      )}
    </S.Card>
  );
};

export default Card;
