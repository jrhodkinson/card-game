package jrh.game.event;

import jrh.game.card.Card;
import jrh.game.match.Match;
import jrh.game.match.Target;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

public class CardPlayedEvent implements Event {

    private final Match match;
    private final Card card;
    private final Target target;

    public CardPlayedEvent(Match match, Card card) {
        this(match, card, null);
    }

    public CardPlayedEvent(Match match, Card card, Target target) {
        this.match = match;
        this.card = card;
        this.target = target;
    }

    public Match getMatch() {
        return match;
    }

    public Card getCard() {
        return card;
    }

    public Optional<Target> getTarget() {
        return Optional.ofNullable(target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("match", match)
                .append("card", card)
                .append("target", target)
                .toString();
    }
}
