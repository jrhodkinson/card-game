package jrh.game.match.api;

import jrh.game.card.Card;

import java.util.List;

public interface Turn {

    int getMoney();

    List<Card> getPlayedCards();
}
