package jrh.game.match;

import jrh.game.card.DiscardPile;
import jrh.game.card.Hand;
import jrh.game.util.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Player implements Target {

    private final String name;
    private final Hand hand;
    private final DiscardPile discardPile = new DiscardPile();
    private int health = Constants.INITIAL_HEALTH;

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount) {
        health -= amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("health", health)
                .append("hand", hand)
                .toString();
    }
}
