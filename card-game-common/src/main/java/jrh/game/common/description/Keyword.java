package jrh.game.common.description;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Keyword {

    public static final Keyword ACQUIRE = new Keyword("acquire");
    public static final Keyword CONSTRUCT = new Keyword("construct");
    public static final Keyword DAMAGE = new Keyword("damage");
    public static final Keyword DRAW = new Keyword("draw");
    public static final Keyword GAIN = new Keyword("gain");
    public static final Keyword HEAL = new Keyword("heal");
    public static final Keyword TAUNT = new Keyword("taunt");
    public static final Keyword UNPLAYABLE = new Keyword("unplayable");
    public static final Keyword VANISH = new Keyword("vanish");

    private final String keyword;

    private Keyword(String keyword) {
        this.keyword = keyword;
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

        return new EqualsBuilder().append(keyword, keyword1.keyword).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(keyword).toHashCode();
    }
}
