package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.event.CardPurchased;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageOnPurchaseBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonValue
    private final int damage;

    public DamageOnPurchaseBehaviour(int damage) {
        super(false);
        this.damage = damage;
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        if (cardPurchased.getCard().equals(this.getCard())) {
            Damageable target = match.getOtherPlayer(cardPurchased.getPlayer());
            logger.info("Damaging player={} by amount={}", target, damage);
            match.getController(HealthController.class).damage(target, this.damage);
        }
    }

    @Override
    public DamageOnPurchaseBehaviour duplicate() {
        return new DamageOnPurchaseBehaviour(damage);
    }
}
