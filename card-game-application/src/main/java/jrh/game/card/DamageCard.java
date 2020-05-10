package jrh.game.card;

import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.match.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageCard extends Card {

    private static final Logger logger = LogManager.getLogger(DamageCard.class);

    private final int damage;

    public DamageCard(int damage) {
        super(1);
        this.damage = damage;
    }

    @Override
    public void play(Match match, Target target) {
        if (target instanceof Player) {
            ((Player) target).damage(this.damage);
        } else {
            logger.error("Invalid target");
        }
    }

    @Override
    public String toString() {
        return String.format("%d damage (%d)", damage, getCost());
    }
}
