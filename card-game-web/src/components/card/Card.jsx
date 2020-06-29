import React from "react";
import * as S from "./styles";

const Card = ({
  card,
  interactable = false,
  selected = false,
  onCardClick = () => {},
}) => {
  return (
    <S.Card
      background={card.color}
      interactable={interactable}
      selected={selected}
      onClick={() => onCardClick(card)}
    >
      <S.CardName background={card.color}>{card.name}</S.CardName>
      <S.CardImage background={card.color} />
      <S.CardDescription background={card.color}>
        {card.description}
      </S.CardDescription>
      <S.CardCost background={card.color}>{card.cost}</S.CardCost>
    </S.Card>
  );
};

export default Card;
