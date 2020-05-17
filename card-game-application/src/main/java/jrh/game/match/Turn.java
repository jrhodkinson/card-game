package jrh.game.match;

import jrh.game.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turn {

    private int money = 0;
    private List<Card> playedCards = new ArrayList<>();

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Card> getPlayedCards() {
        return Collections.unmodifiableList(playedCards);
    }

    void addPlayedCard(Card card) {
        playedCards.add(card);
    }

    boolean removePlayedCard(Card card) {
       return playedCards.remove(card);
    }

    void clearPlayedCards() {
        playedCards.clear();
    }
}
