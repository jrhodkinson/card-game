package jrh.game.card.behaviour;

import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.match.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageBehaviour implements Behaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    private final int damage;

    public DamageBehaviour(int damage) {
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
}
