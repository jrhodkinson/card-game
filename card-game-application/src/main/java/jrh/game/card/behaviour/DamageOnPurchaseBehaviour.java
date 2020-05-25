package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPurchased;
import jrh.game.match.Match;
import jrh.game.match.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageOnPurchaseBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonValue
    private final int damage;

    public DamageOnPurchaseBehaviour(int damage) {
        this.damage = damage;
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        if (cardPurchased.getCard().equals(this.getCard())) {
            Player target = match.getOtherPlayer(cardPurchased.getPlayer());
            logger.info("Damaging player={} by amount={}", target, damage);
            match.getHealthController().damage(target, this.damage);
        }
    }

    @Override
    public DamageOnPurchaseBehaviour duplicate() {
        return new DamageOnPurchaseBehaviour(damage);
    }
}
