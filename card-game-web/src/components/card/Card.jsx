import React from "react";
import * as S from "./styles";

const Card = ({
  card,
  interactable = false,
  selected = false,
  onCardClick = () => {},
  hideDescription = false,
}) => {
  return (
    <S.Card
      background={card.color}
      hideDescription={hideDescription}
      interactable={interactable}
      selected={selected}
      onClick={() => onCardClick(card)}
    >
      <S.CardCost background={card.color}>{card.cost}</S.CardCost>
      <S.CardName background={card.color}>{card.name}</S.CardName>
      <S.CardImage background={card.color} />
      {hideDescription || (
        <S.CardDescription background={card.color}>
          {card.description}
        </S.CardDescription>
      )}
    </S.Card>
  );
};

export default Card;
