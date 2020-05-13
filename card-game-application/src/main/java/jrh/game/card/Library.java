package jrh.game.card;

import java.util.List;

public interface Library {

    Card getCard(CardId cardId);

    List<Card> getAllCards();
}
