package jrh.game.asset;

import jrh.game.card.CardImpl;
import jrh.game.common.CardId;

import java.util.List;
import java.util.Optional;

public interface CardImplLibrary {

    Optional<CardImpl> getCard(CardId cardId);

    List<CardId> getAllCardIds();
}
