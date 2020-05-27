package jrh.game.asset;

import jrh.game.card.Card;
import jrh.game.card.CardId;

import java.util.List;

public interface CardLibrary {

    Card getCard(CardId cardId);

    Card getDebugCard(CardId cardId);

    List<Card> getAllCards();
}
