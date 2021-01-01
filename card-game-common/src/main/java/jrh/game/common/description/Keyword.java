package jrh.game.common.description;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Keyword {

    public static final Keyword ACQUIRE = new Keyword("acquire",
            "Accumulate mammon (M), used to purchase cards during the current turn only");
    public static final Keyword CONSTRUCT = new Keyword("construct", "Build a structure");
    public static final Keyword DAMAGE = new Keyword("damage", "Remove health from a player or structure's life total");
    public static final Keyword DRAW = new Keyword("draw", "Pick up cards from your deck");
    public static final Keyword GAIN = new Keyword("gain",
            "Add a card to your play area, which will be discarded and join your deck");
    public static final Keyword HEAL = new Keyword("heal", "Add health to a player or structure's life total");
    public static final Keyword PURGE = new Keyword("purge", "Remove a card from the store");
    public static final Keyword TAUNT = new Keyword("taunt", "Any attacks must be directed at structures with taunt");
    public static final Keyword UNPLAYABLE = new Keyword("unplayable", "Cannot be played");
    public static final Keyword VANISH = new Keyword("vanish", "After being played, is removed from the game");

    private final String keyword;
    private final String help;

    private Keyword(String keyword, String help) {
        this.keyword = keyword;
        this.help = help;
    }

    public String getHelp() {
        return help;
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Keyword keyword1 = (Keyword) o;

        return new EqualsBuilder().append(keyword, keyword1.keyword).append(help, keyword1.help).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(keyword).append(help).toHashCode();
    }
}
