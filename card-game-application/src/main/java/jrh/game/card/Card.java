package jrh.game.card;

import jrh.game.match.Match;
import jrh.game.match.Target;

public abstract class Card {

    private final int cost;

    public Card(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    abstract public void play(Match match, Target target);
}
