package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardPlayed;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("sacrifice")
public class SacrificeBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(SacrificeBehaviour.class);

    @JsonValue
    private final int damage;

    public SacrificeBehaviour(int damage) {
        super(false);
        this.damage = damage;
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
