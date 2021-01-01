package jrh.game.api;

import java.util.List;

public interface Store {

    List<Card> getRow();

    Card getNext();
}
