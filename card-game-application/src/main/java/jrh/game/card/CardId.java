package jrh.game.card;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class CardId {

    public static final CardId COPPER = new CardId("copper");
    public static final CardId SILVER = new CardId("silver");
    public static final CardId GOLD = new CardId("gold");
    public static final CardId VANISHING_GOLD = new CardId("vanishing-gold");

    public static final CardId KNIFE = new CardId("knife");
    public static final CardId SWORD = new CardId("sword");

    public static final CardId DRAW_1 = new CardId("draw-1");
    public static final CardId DRAW_2 = new CardId("draw-2");
    public static final CardId DRAW_MONEY = new CardId("draw-money");

    private final String cardId;

    public CardId(String cardId) {
        this.cardId = cardId;
    }

    @JsonValue
    @Override
    public String toString() {
        return cardId;
    }

    public boolean isDebugId() {
        return cardId.startsWith(Debug.PREFIX + ":");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CardId cardId1 = (CardId) o;

        return new EqualsBuilder().append(cardId, cardId1.cardId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(cardId).toHashCode();
    }

    public static class Debug {
        public static final String PREFIX = "debug";

        public static final CardId MONEY = new CardId("debug:money");
        public static final CardId DRAW = new CardId("debug:draw");
        public static final CardId DAMAGE = new CardId("debug:damage");

        private Debug() {
            // not instantiable
        }
    }

}
