package jrh.game;

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
