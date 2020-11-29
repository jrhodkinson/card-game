import React from "react";
import * as S from "./styles";

const Card = ({
  card,
  interactable = false,
  selected = false,
  onCardClick = () => {},
  short = false,
}) => {
  return (
    <S.Card
      short={short}
      interactable={interactable}
      selected={selected}
      onClick={() => onCardClick(card)}
    >
      <S.CardCost background={card.color}>{card.cost}</S.CardCost>
      <S.CardName background={card.color} title={card.flavor}>
        {card.name}
      </S.CardName>
      {short ? (
        <S.ShortCardDescription background={card.color}>
          {card.description}
        </S.ShortCardDescription>
      ) : (
        <>
          <S.CardImage background={card.color} />
          <S.CardDescription background={card.color}>
            {card.description}
          </S.CardDescription>
        </>
      )}
    </S.Card>
  );
};

export default Card;
