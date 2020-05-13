package jrh.game.card;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class CardId {

    private final String cardId;

    public CardId(String cardId) {
        this.cardId = cardId;
    }

    @JsonValue
    @Override
    public String toString() {
        return cardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CardId cardId1 = (CardId) o;

        return new EqualsBuilder()
                .append(cardId, cardId1.cardId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(cardId)
                .toHashCode();
    }
}
