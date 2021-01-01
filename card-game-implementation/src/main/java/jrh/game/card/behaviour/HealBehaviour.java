package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardPlayed;
import jrh.game.common.description.AtomicDescription;
import jrh.game.api.Subscribe;
import jrh.game.common.description.Keyword;
import jrh.game.match.HealthController;
import jrh.game.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.DAMAGEABLE;

@JsonKey("heal")
public class HealBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(HealBehaviour.class);

    @JsonValue
    private final int health;

    public HealBehaviour(int health) {
        super(DAMAGEABLE);
        this.health = health;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.HEAL).number(health).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getDamageableTarget().isPresent()) {
            logger.info("Healing {} by {}", cardPlayed.getDamageableTarget().get(), this.health);
            match
                .getController(HealthController.class)
                .heal(cardPlayed.getPlayer(),
                        cardPlayed.getDamageableTarget().get(), this.health);
        }
    }

    @Override
    public HealBehaviour duplicate() {
        return new HealBehaviour(health);
    }
}
