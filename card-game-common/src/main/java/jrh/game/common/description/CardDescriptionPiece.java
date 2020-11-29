package jrh.game.common.description;

import jrh.game.common.CardId;

public class CardDescriptionPiece implements DescriptionPiece {

    private final CardId cardId;

    public CardDescriptionPiece(CardId cardId) {
        this.cardId = cardId;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return descriptionContext.getName(cardId);
    }
}
