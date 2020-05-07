package jrh.game;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Player {

    private final String name;
    private final Hand hand;
    private int health = 50;

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
