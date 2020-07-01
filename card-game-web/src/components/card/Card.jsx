import React, { useState } from "react";
import * as S from "./styles";

const Card = ({
  card,
  interactable = false,
  selected = false,
  onCardClick = () => {},
  hideDescription = false,
}) => {
  const [mouseOver, setMouseOver] = useState(false);
  return (
    <S.Card
      background={card.color}
      hideDescription={hideDescription}
      interactable={interactable}
      selected={selected}
      onClick={() => onCardClick(card)}
      onMouseOver={() => setMouseOver(true)}
      onMouseOut={() => setMouseOver(false)}
    >
      <S.CardCost background={card.color}>{card.cost}</S.CardCost>
      <S.CardName background={card.color}>{card.name}</S.CardName>
      <S.CardImage background={card.color} />
      {hideDescription ? (
        mouseOver && (
          <S.CardDescriptionOverlay background={card.color}>
            {card.description}
          </S.CardDescriptionOverlay>
        )
      ) : (
        <S.CardDescription background={card.color}>
          {card.description}
        </S.CardDescription>
      )}
    </S.Card>
  );
};

export default Card;
