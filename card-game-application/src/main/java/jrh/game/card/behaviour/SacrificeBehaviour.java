package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardPlayed;
import jrh.game.common.BehaviourDescription;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.api.Match;
import jrh.game.api.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("sacrifice")
public class SacrificeBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(SacrificeBehaviour.class);

    @JsonValue
    private final int damage;

    public SacrificeBehaviour(int damage) {
        super(false);
        this.damage = damage;
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.builder().keyword("Damage").plainString("self").number(damage).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            Player target = cardPlayed.getPlayer();
            logger.info("Damaging player={} by amount={}", target, damage);
            match.getController(HealthController.class).damage(cardPlayed.getPlayer(), target, this.damage);
        }
    }

    @Override
    public SacrificeBehaviour duplicate() {
        return new SacrificeBehaviour(damage);
    }
}
