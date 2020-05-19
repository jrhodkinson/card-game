package jrh.game.card;

import java.util.List;

public interface Library {

    Card getCard(CardId cardId);

    Card getDebugCard(CardId cardId);

    List<Card> getAllCards();
}
