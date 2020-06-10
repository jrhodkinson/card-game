package jrh.game.asset;

import jrh.game.card.CardImpl;
import jrh.game.common.CardId;

import java.util.List;

public interface CardImplLibrary {

    CardImpl getCard(CardId cardId);

    List<CardImpl> getAllCards();
}
