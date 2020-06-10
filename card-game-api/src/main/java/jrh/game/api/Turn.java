package jrh.game.api;

import java.util.List;

public interface Turn {

    int getMoney();

    List<Card> getPlayedCards();
}
